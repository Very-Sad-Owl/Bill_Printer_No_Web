package ru.clevertec.tasks.olga.service.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.CardNotFoundException;
import ru.clevertec.tasks.olga.exception.CartNotFoundException;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.DiscountType;
import ru.clevertec.tasks.olga.service.DiscountCardService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardServiceImplTest {

    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    DiscountCardService discountCardService = new DiscountCardServiceImpl();

    @Test
    void findById_existingNode_DiscountCardObj() throws ParseException {
        int id = 1;

        DiscountCard expected = new DiscountCard(id, formatter.parse("11.11.2011"),
                DiscountType.NONE);

        DiscountCard actual = discountCardService.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findById_nonexistingNode_exc(){
        int id = -1;
        assertThrows(CardNotFoundException.class, () -> {
            discountCardService.findById(id);
        });
    }

    @Test
    void getAll() throws ParseException {
        List<DiscountCard> excected = new ArrayListImpl<>();
        excected.add(new DiscountCard(1, formatter.parse("11.11.2011"),
                DiscountType.NONE));
        excected.add(new DiscountCard(2, formatter.parse("15.03.2001"),
                DiscountType.GOLD));

        List<DiscountCard> actual = discountCardService.getAll();

        assertEquals(excected, actual);
    }
}