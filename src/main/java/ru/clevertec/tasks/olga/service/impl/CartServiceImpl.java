package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.CartNotFoundException;
import ru.clevertec.tasks.olga.exception.InvalidArgException;
import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.Slot;
import ru.clevertec.tasks.olga.model.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.repository.CartRepository;
import ru.clevertec.tasks.olga.repository.impl.CartRepositoryImpl;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.CashierService;
import ru.clevertec.tasks.olga.service.DiscountCardService;
import ru.clevertec.tasks.olga.service.ProductService;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class CartServiceImpl extends AbstractService<Cart, CartRepository> implements CartService {

    private CartRepository cartRepository = new CartRepositoryImpl();
    private ProductService productService = new ProductServiceImpl();
    private CashierService cashierService = new CashierServiceImpl();
    private DiscountCardService cardService = new DiscountCardServiceImpl();

    @Override
    public long save(Cart cart) {
        return cartRepository.save(cart);
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
    public boolean delete(Cart cart, String filePath) {
        return false;
    }

    @Override
    public Cart update(Cart cart, String filePath) {
        return null;
    }

    @Override
    public List<Slot> formSlots(Map<Long, Integer> goods) {
        List<Slot> slots = new ArrayListImpl<>();
        for(Map.Entry<Long,Integer> entry : goods.entrySet()){
            slots.add(new Slot(productService.findById(entry.getKey()), entry.getValue()));
        }
        return slots;
    }

    @Override
    public Cart formCart(CartParamsDTO cartParamsDTO) {
        Cart cart =  Cart.builder()
                .positions(formSlots(cartParamsDTO.goods))
                .discountCard(cardService.findById(cartParamsDTO.card_id))
                .cashier(cashierService.findById(cartParamsDTO.cashier_id))
                .build();
        cart.calculatePrice();
        return cart;

    }

    @Override
    public void printBill(Cart cart) {

    }
}
