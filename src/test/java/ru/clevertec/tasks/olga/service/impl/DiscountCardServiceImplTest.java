package ru.clevertec.tasks.olga.service.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.tasks.olga.exception.CardNotFoundException;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.DiscountType;
import ru.clevertec.tasks.olga.service.DiscountCardService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        DiscountCard expected = new DiscountCard(id, formatter.parse("11.11.2011"),
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
    void getAll() throws ParseException {
        List<DiscountCard> excected = new ArrayList<>();
        excected.add(new DiscountCard(1, formatter.parse("11.11.2011"),
                DiscountType.NONE));
        excected.add(new DiscountCard(2, formatter.parse("15.03.2001"),
                DiscountType.GOLD));

        List<DiscountCard> actual = discountCardService.getAll(DB_PATH);

        assertEquals(excected, actual);
    }
}