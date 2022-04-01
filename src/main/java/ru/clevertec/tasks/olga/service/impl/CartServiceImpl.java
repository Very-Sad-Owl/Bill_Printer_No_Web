package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.CartNotFoundException;
import ru.clevertec.tasks.olga.exception.InvalidArgException;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.util.printer.impl.PdfPrinter;
import ru.clevertec.tasks.olga.repository.CartRepository;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.CashierService;
import ru.clevertec.tasks.olga.service.DiscountCardService;
import ru.clevertec.tasks.olga.service.ProductService;
import lombok.NoArgsConstructor;
import ru.clevertec.tasks.olga.util.formatter.AbstractBillFormatter;
import ru.clevertec.tasks.olga.util.formatter.PseudographicBillFormatter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@NoArgsConstructor
public class CartServiceImpl
        extends AbstractService<Cart, CartParamsDTO, CartRepository>
        implements CartService {

    private static final CartRepository cartRepository = repoFactory.getCartRepo();
    private static final ProductService productService = new ProductServiceImpl();
    private static final CashierService cashierService = new CashierServiceImpl();
    private static final DiscountCardService cardService = new DiscountCardServiceImpl();
    private static final PdfPrinter pdfPrinter = new PdfPrinter();

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
            throw new InvalidArgException("error.invalid_arg");
        }
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()){
            return cart.get();
        } else {
            throw new CartNotFoundException("error.cart_not_found");
        }
    }

    @Override
    public List<Cart> getAll(int limit, int offset) {
        List<Cart> bills = cartRepository.getAll(limit, offset);
        if (bills.isEmpty()){
            throw new CartNotFoundException("error.cart_not_found");
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

        if (updated == original) throw new WritingException(); //TODO: nothing to update exception
        if (cartRepository.update(updated)) {
            return updated;
        } else {
            throw new ReadingException();
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
        AbstractBillFormatter formatter = new PseudographicBillFormatter(locale);
        String absoluteWebPath = System.getProperty("catalina.home") + "/bin/";
        return Paths.get(absoluteWebPath + pdfPrinter.print(formatter.format(cart)));
    }
}
