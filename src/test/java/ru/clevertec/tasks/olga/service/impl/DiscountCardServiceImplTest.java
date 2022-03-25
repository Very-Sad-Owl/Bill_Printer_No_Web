package ru.clevertec.tasks.olga.service.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.tasks.olga.exception.CardNotFoundException;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.DiscountType;
import ru.clevertec.tasks.olga.service.DiscountCardService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardServiceImplTest {

    private static final String DB_PATH = System.getProperty("user.dir")+"\\db\\test_db\\";
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private DiscountCardService discountCardService = new DiscountCardServiceImpl();

    @Test
    void findById_existingNode_DiscountCardObj() throws ParseException {
        int id = 1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse("11.11.2011", formatter);
        DiscountCard expected = new DiscountCard(id, localDate,
                DiscountType.NONE);

        DiscountCard actual = discountCardService.findById(id, DB_PATH);

        assertEquals(expected, actual);
    }

    @Test
    void findById_nonexistingNode_exc(){
        int id = -1;
        assertThrows(CardNotFoundException.class, () -> {
            discountCardService.findById(id, DB_PATH);
        });
    }

    @Test
    void getAll(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate1 = LocalDate.parse("15.03.2001", formatter);
        LocalDate localDate2 = LocalDate.parse("11.11.2011", formatter);

        List<DiscountCard> expected = new ArrayList<>();
        expected.add(new DiscountCard(1, localDate2,
                DiscountType.NONE));
        expected.add(new DiscountCard(2, localDate1,
                DiscountType.GOLD));

        List<DiscountCard> actual = discountCardService.getAll(DB_PATH);

        assertEquals(expected, actual);
    }
}