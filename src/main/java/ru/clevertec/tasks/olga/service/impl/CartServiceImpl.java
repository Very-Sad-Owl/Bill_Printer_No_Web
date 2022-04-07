package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.exception.crud.notfound.BillNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.ProductNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.repository.RepositoryException;
import ru.clevertec.tasks.olga.util.printer.impl.PdfPrinter;
import ru.clevertec.tasks.olga.repository.CartRepository;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.CashierService;
import ru.clevertec.tasks.olga.service.DiscountCardService;
import ru.clevertec.tasks.olga.service.ProductService;
import ru.clevertec.tasks.olga.util.printer.template.AbstractBillFormatter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static ru.clevertec.tasks.olga.util.validation.CRUDParamsValidator.*;

@Service
public class CartServiceImpl
        extends AbstractService
        implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CashierService cashierService;
    private final DiscountCardService cardService;
    private final PdfPrinter pdfPrinter;
    private final AbstractBillFormatter formatter;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductService productService, CashierService cashierService,
                           DiscountCardService cardService, PdfPrinter pdfPrinter, AbstractBillFormatter formatter) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cashierService = cashierService;
        this.cardService = cardService;
        this.pdfPrinter = pdfPrinter;
        this.formatter = formatter;
    }


    @Override
    public Cart save(CartParamsDTO dto) {
        validateFullyFilledDto(dto);
        try {
            Cart cart = formCart(dto);
            long insertedId = cartRepository.save(cart);
            cart.setId(insertedId);
            return cart;
        } catch (RepositoryException | NotFoundExceptionHandled e) {
            throw new SavingExceptionHandled(e);
        }
    }

    @Override
    public Cart findById(long id) {
        validateId(id);
        try {
            Optional<Cart> cart = cartRepository.findById(id);
            if (cart.isPresent()) {
                return cart.get();
            } else {
                throw new BillNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<Cart> getAll(int limit, int offset) {
        return cartRepository.getAll(limit, offset);
    }

    @Override
    public void delete(long id) {
        validateId(id);
        try {
            if (!cartRepository.delete(id)) {
                throw new BillNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e);
        }
    }

    @Override
    public Cart put(CartParamsDTO dto) {
        validateFullyFilledDto(dto);
        try {
            Cart updated = formCart(dto);
            if (!cartRepository.update(updated)) {
                throw new UpdatingExceptionHandled(new BillNotFoundExceptionHandled(dto.id + ""));
            }
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    public Cart patch(CartParamsDTO dto) {
        validatePartlyFilledObject(dto);
        Cart updated;
        Cart original;
        try {
            original = findById(dto.id);
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
            updated = formCart(newCart);
        } catch (NotFoundExceptionHandled e) {
            throw new UpdatingExceptionHandled(e);
        }

        if (dto.products == null || dto.products.isEmpty()) {
            updated.setPositions(original.getPositions());
        } else {
            for (Slot newSlot : updated.getPositions()) {
                for (Slot slot : original.getPositions()) {
                    if (newSlot.getProduct().getId() == slot.getProduct().getId()) {
                        newSlot.setId(slot.getId());
                    }
                }
            }
        }
        updated.calculatePrice();
        try {
            cartRepository.update(updated);
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    public List<Slot> formSlots(Map<Long, Integer> goods) {
        List<Slot> slots = new ArrayListImpl<>();
        for (Map.Entry<Long, Integer> entry : goods.entrySet()) {
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
        try {
            Cart cart = Cart.builder()
                    .id(cartParamsDTO.id)
                    .positions(formSlots(cartParamsDTO.products))
                    .discountCard(cardService.findById(cartParamsDTO.card_uid))
                    .cashier(cashierService.findById(cartParamsDTO.cashier_uid))
                    .build();
            cart.calculatePrice();
            return cart;
        } catch (NotFoundExceptionHandled e) {
            throw new NotFoundExceptionHandled(e);
        }
    }

    @Override
    public Path printBill(Cart cart, Locale locale) {
        String absoluteWebPath = System.getProperty("catalina.home") + "/bin/";
        return Paths.get(absoluteWebPath + pdfPrinter.print(formatter.format(cart, locale)));
    }
}
