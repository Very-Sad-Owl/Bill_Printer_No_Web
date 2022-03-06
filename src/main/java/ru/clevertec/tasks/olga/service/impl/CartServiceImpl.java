package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.Slot;
import ru.clevertec.tasks.olga.repository.models_repo.CartRepository;
import ru.clevertec.tasks.olga.service.models_service.CartService;
import ru.clevertec.tasks.olga.service.models_service.ProductService;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@NoArgsConstructor
public class CartServiceImpl extends AbstractService<Cart, CartRepository> implements CartService {

    private ProductService productService = new ProductServiceImpl();
    private CartRepository cartRepository = repositoryFactory.getCartRepo();


    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart findById(long id) {
        return cartRepository.findById(id);
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.getAll();
    }

    @Override
    public List<Slot> formSlots(Map<Long, Integer> goods) {
        List<Slot> slots = new ArrayList<>();
        for(Map.Entry<Long,Integer> entry : goods.entrySet()){
            slots.add(new Slot(productService.findById(entry.getKey()), entry.getValue()));
        }
        return slots;
    }

    @Override
    public Cart formCart(List<Slot> goods, DiscountCard card, Cashier cashier) {
        return new Cart(new Random(System.currentTimeMillis()).nextLong(), goods, card, cashier);

    }

    @Override
    public void printBill(Cart cart, String destinationPath) {
        cartRepository.printBill(cart, destinationPath);
    }
}
