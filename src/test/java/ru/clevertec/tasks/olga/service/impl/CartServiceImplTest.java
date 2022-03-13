package ru.clevertec.tasks.olga.service.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.tasks.olga.exception.CartNotFoundException;
import ru.clevertec.tasks.olga.model.*;
import ru.clevertec.tasks.olga.service.CartService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartServiceImplTest {

    private static final String DB_PATH = System.getProperty("user.dir")+"\\db\\test_db\\";
    private CartService cartService = new CartServiceImpl();

    @Test
    void formSlots() {
        Map<Long, Integer> goods = new HashMap<>();
        goods.put(1L, 5);
        goods.put(2L, 2);

        List<Slot> expected  = new ArrayList<>();
        expected.add(new Slot(new Product(1, "milk", 1.43, ProductDiscountType.MORE_THAN_FIVE), 5));
        expected.add(new Slot(new Product(2, "bread", 1.20, ProductDiscountType.NONE), 2));

        List<Slot> actual = cartService.formSlots(goods, DB_PATH);

        assertEquals(expected, actual);
    }

    @Test
    void formCart() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        Map<Long, Integer> goods = new HashMap<>();
        goods.put(1L, 5);
        goods.put(2L, 2);

        List<Slot> slots  = new ArrayList<>();
        slots.add(new Slot(new Product(1, "milk", 1.43, ProductDiscountType.MORE_THAN_FIVE), 5));
        slots.add(new Slot(new Product(2, "bread", 1.20, ProductDiscountType.NONE), 2));

        DiscountCard discountCard = new DiscountCard(2, formatter.parse("15.03.2001"), DiscountType.GOLD);

        Cashier cashier = new Cashier(2, "Olga", "Mailychko");


        Cart expected = new Cart(1, slots, discountCard, cashier);

        Cart actual = cartService.formCart(slots, discountCard, cashier);
        actual.setId(1);

        assertEquals(expected, actual);
    }

    @Test
    void getALl(){
        List<Cart> carts = cartService.getAll(DB_PATH);
        System.out.println(carts);
    }

    @Test
    void findById_existingNode_cartObj() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        long id = 1;
        List<Slot> slots  = new ArrayList<>();
        slots.add(new Slot(new Product(1, "milk", 1.43, ProductDiscountType.MORE_THAN_FIVE), 1));
        DiscountCard discountCard = new DiscountCard(1, formatter.parse("15.03.2001"), DiscountType.SILVER);
        Cashier cashier = new Cashier(2, "Oleg", "Geosimp");

        Cart expected = new Cart(id, slots, discountCard, cashier);

        Cart actual = cartService.findById(id, DB_PATH);

        assertEquals(expected, actual);
    }

    @Test
    void findById_nonexistingNode_exception(){
        int id = -1;
        assertThrows(CartNotFoundException.class, () -> {
            cartService.findById(id, DB_PATH);
        });
    }

}