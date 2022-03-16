package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.Slot;
import ru.clevertec.tasks.olga.repository.CartRepository;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.ProductService;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Random;

@NoArgsConstructor
public class CartServiceImpl extends AbstractService<Cart, CartRepository> implements CartService {

    private ProductService productService = new ProductServiceImpl();
    private CartRepository cartRepository = repositoryFactory.getCartRepo();


    @Override
    public void save(Cart cart, String dbPath) {
        cartRepository.save(cart, dbPath);
    }

    @Override
    public Cart findById(long id, String dbPath) {
        return cartRepository.findById(id, dbPath);
    }

    @Override
    public List<Cart> getAll(String dbPath) {
        return cartRepository.getAll(dbPath);
    }

    @Override
    public boolean delete(Cart cart, String filePath) {
        return false;
    }

    @Override
    public Cart update(Cart cart, String filePath) {
        return cart;
    }

    @Override
    public List<Slot> formSlots(Map<Long, Integer> goods, String dbPath) {
        List<Slot> slots = new ArrayListImpl<>();
        for(Map.Entry<Long,Integer> entry : goods.entrySet()){
            slots.add(new Slot(productService.findById(entry.getKey(), dbPath), entry.getValue()));
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
