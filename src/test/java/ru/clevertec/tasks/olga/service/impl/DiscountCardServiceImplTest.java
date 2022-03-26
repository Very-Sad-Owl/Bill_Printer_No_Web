//package ru.clevertec.tasks.olga.service.impl;
//
//import org.junit.jupiter.api.Test;
//import ru.clevertec.tasks.olga.exception.CardNotFoundException;
//import ru.clevertec.tasks.olga.model.DiscountCard;
//import ru.clevertec.tasks.olga.model.CardDiscountType;
//import ru.clevertec.tasks.olga.service.DiscountCardService;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DiscountCardServiceImplTest {
//
//    private DiscountCardService discountCardService = new DiscountCardServiceImpl();
//
//    @Test
//    void findById_existingNode_DiscountCardObj() throws ParseException {
//        int id = 1;
//
//        DiscountCard expected = new DiscountCard(id, LocalDate.parse("2001-11-11"),
//                CardDiscountType.builder().title("NONE").discount(0).build());
//
//        DiscountCard actual = discountCardService.findById(id);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findById_nonexistingNode_exc(){
//        int id = -1;
//        assertThrows(CardNotFoundException.class, () -> {
//            discountCardService.findById(id);
//        });
//    }
//
//    @Test
//    void getAll() throws ParseException {
//        List<DiscountCard> excected = new ArrayList<>();
//        excected.add(new DiscountCard(1, LocalDate.parse("2001-11-11"),
//                CardDiscountType.builder().title("NONE").discount(0).build()));
//        excected.add(new DiscountCard(2, LocalDate.parse("2001-03-15"),
//                CardDiscountType.builder().title("GOLD").discount(7).build()));
//
//        List<DiscountCard> actual = discountCardService.getAll();
//
//        assertEquals(excected, actual);
//    }
//}