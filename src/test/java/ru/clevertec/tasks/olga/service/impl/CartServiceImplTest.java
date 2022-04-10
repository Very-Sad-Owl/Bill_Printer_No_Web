//package ru.clevertec.tasks.olga.service.impl;
//
//import org.junit.jupiter.api.Test;
//import ru.clevertec.tasks.olga.entity.DiscountCard;
//import ru.clevertec.tasks.olga.entity.Product;
//import ru.clevertec.tasks.olga.entity.ProductDiscountType;
//import ru.clevertec.tasks.olga.entity.Slot;
//import ru.clevertec.tasks.olga.exception.CartNotFoundException;
//import ru.clevertec.tasks.olga.service.CartService;
//import java.text.ParseException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class CartServiceImplTest {
//
//    private CartService cartService = new CartServiceImpl();
//
//    @Test
//    void formSlots() {
//        Map<Long, Integer> goods = new HashMap<>();
//        goods.put(1L, 5);
//        goods.put(2L, 2);
//
//        List<Slot> expected  = new ArrayList<>();
//        expected.add(new Slot(new Product(1, "milk", 1.43,
//                ProductDiscountType.builder().title("MORE_THAN_FIVE").discount(10).requiredMinQuantity(5).build()), 5));
//        expected.add(new Slot(new Product(2, "bread", 1.20,
//                ProductDiscountType.builder().title("NONE").discount(0).requiredMinQuantity(0).build()), 2));
//
//        List<Slot> actual = cartService.formSlots(goods);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void formCart() throws ParseException {
//        Map<Long, Integer> goods = new HashMap<>();
//        goods.put(1L, 5);
//        goods.put(2L, 2);
//
//        List<Slot> slots  = new ArrayList<>();
//        slots.add(new Slot(new Product(1, "milk", 1.43,
//                ProductDiscountType.builder().
//                title("MORE_THAN_FIVE").discount(10).requiredMinQuantity(5).build()), 5));
//        slots.add(new Slot(new Product(2, "bread", 1.20, ProductDiscountType.builder()
//                .title("NONE").discount(0).requiredMinQuantity(0).build()), 2));
//
//        DiscountCard discountCard = new DiscountCard(2, LocalDate.parse("2001-03-15"),
//                CardDiscountType.builder().title("GOLD").discount(7).build());
//
//        Cashier cashier = new Cashier(2, "Olga", "Mailychko");
//
//
//        Cart expected = new Cart(1, slots, discountCard, cashier);
//
//        Cart actual = cartService.formCart(CartDto.builder().goods(goods).discountCardId(2).cashierId(2).build());
//        actual.setId(1);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void getALl(){
//        List<Cart> carts = cartService.getAll();
//        System.out.println(carts);
//    }
//
//    @Test
//    void findById_existingNode_cartObj() throws ParseException {
//        long id = 1;
//        List<Slot> slots  = new ArrayList<>();
//        slots.add(new Slot(new Product(1, "milk", 1.43,
//                ProductDiscountType.builder().title("MORE_THAN_FIVE").discount(10).requiredMinQuantity(5).build()), 1));
//        DiscountCard discountCard = new DiscountCard(1, LocalDate.parse("2001-03-15"),
//                CardDiscountType.builder().title("SILVE").discount(5).build());
//        Cashier cashier = new Cashier(2, "Oleg", "Geosimp");
//
//        Cart expected = new Cart(id, slots, discountCard, cashier);
//
//        Cart actual = cartService.findById(id);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findById_nonexistingNode_exception(){
//        int id = -1;
//        assertThrows(CartNotFoundException.class, () -> {
//            cartService.findById(id);
//        });
//    }
//
//}