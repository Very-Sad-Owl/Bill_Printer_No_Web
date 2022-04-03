package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.CartNotFoundExceptionCustom;
import ru.clevertec.tasks.olga.exception.InvalidArgExceptionCustom;
import ru.clevertec.tasks.olga.exception.ReadingExceptionCustom;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.util.printer.impl.PdfPrinter;
import ru.clevertec.tasks.olga.repository.CartRepository;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.CashierService;
import ru.clevertec.tasks.olga.service.DiscountCardService;
import ru.clevertec.tasks.olga.service.ProductService;
import ru.clevertec.tasks.olga.util.printer.template.AbstractBillFormatter;
import ru.clevertec.tasks.olga.util.printer.template.PseudographicBillFormatter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CartServiceImpl
        extends AbstractService
        implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CashierService cashierService;
    private final DiscountCardService cardService;
    private final PdfPrinter pdfPrinter;
    private AbstractBillFormatter formatter;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService, CashierService cashierService,
                           DiscountCardService cardService, PdfPrinter pdfPrinter, AbstractBillFormatter formatter) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cashierService = cashierService;
        this.cardService = cardService;
        this.pdfPrinter = pdfPrinter;
        this.formatter = formatter;
    }

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductService productService, CashierService cashierService,
                           DiscountCardService cardService, PdfPrinter pdfPrinter) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cashierService = cashierService;
        this.cardService = cardService;
        this.pdfPrinter = pdfPrinter;
    }

    @Override
    public Cart save(CartParamsDTO dto) {
        Cart cart = formCart(dto);
        long insertedId = cartRepository.save(cart);
        cart.setId(insertedId);
        return cart;
    }

    @Override
    public Cart findById(long id) {
        if (id < 0){
            throw new InvalidArgExceptionCustom("error.invalid_arg");
        }
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()){
            return cart.get();
        } else {
            throw new CartNotFoundExceptionCustom("error.cart_not_found");
        }
    }

    @Override
    public List<Cart> getAll(int limit, int offset) {
        List<Cart> bills = cartRepository.getAll(limit, offset);
        if (bills.isEmpty()){
            throw new CartNotFoundExceptionCustom("error.cart_not_found");
        }
        return bills;
    }

    @Override
    public boolean delete(long id) {
        return cartRepository.delete(id);
    }

    @Override
    public Cart update(CartParamsDTO dto) {
        Cart original = findById(dto.id);
        CartParamsDTO newCart = CartParamsDTO.builder()
                .id(dto.id)
                .card_uid(dto.card_uid != Defaults.defaultValue(Long.TYPE)
                        ? dto.card_uid
                        : original.getDiscountCard().getId())
                .cashier_uid(dto.cashier_uid != Defaults.defaultValue(Long.TYPE)
                ? dto.cashier_uid
                : original.getCashier().getId())
                .build();
        newCart.products = dto.products;
        Cart updated = formCart(newCart);

        if (dto.products == null || dto.products.isEmpty()){
            updated.setPositions(original.getPositions());
        } else {
            for (Slot newSlot : updated.getPositions()){
                for (Slot slot : original.getPositions()) {
                    if (newSlot.getProduct().getId() == slot.getProduct().getId()) {
                        newSlot.setId(slot.getId());
                    }
                }
            }
        }
        updated.calculatePrice();
        if (cartRepository.update(updated)) {
            return updated;
        } else {
            throw new ReadingExceptionCustom();
        }
    }

    @Override
    public List<Slot> formSlots(Map<Long, Integer> goods) {
        List<Slot> slots = new ArrayListImpl<>();
        for(Map.Entry<Long,Integer> entry : goods.entrySet()){
            Slot slot = new Slot(productService.findById(entry.getKey()), entry.getValue());
            slots.add(slot);
        }
        return slots;
    }

    @Override
    public Slot formSlot(Map.Entry<Long, Integer> pair) {
        return Slot.builder()
                .product(productService.findById((pair.getKey())))
                .quantity(pair.getValue())
                .build();
    }

    @Override
    public Cart formCart(CartParamsDTO cartParamsDTO) {
        Cart cart =  Cart.builder()
                .id(cartParamsDTO.id)
                .positions(formSlots(cartParamsDTO.products))
                .discountCard(cardService.findById(cartParamsDTO.card_uid))
                .cashier(cashierService.findById(cartParamsDTO.cashier_uid))
                .build();
        cart.calculatePrice();
        return cart;

    }

    @Override
    public Path printBill(Cart cart, Locale locale) {
        String absoluteWebPath = System.getProperty("catalina.home") + "/bin/";
        return Paths.get(absoluteWebPath + pdfPrinter.print(formatter.format(cart, locale)));
    }
}
