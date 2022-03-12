package ru.clevertec.tasks.olga.service.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.CashierNotFoundException;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.service.CashierService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashierServiceImplTest {

    CashierService cashierService = new CashierServiceImpl();

    @Test
    void getAll(){
        List<Cashier> expected = new ArrayListImpl<>();
        expected.add(new Cashier(1, "Oleg", "Geosimp"));
        expected.add(new Cashier(2, "Olga", "Mailychko"));

        List<Cashier> actual = cashierService.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void findById_existingNode_cashierObj(){
        int id = 2;

        Cashier excpected = new Cashier(id, "Olga", "Mailychko");

        Cashier actual = cashierService.findById(id);

        assertEquals(excpected, actual);
    }

    @Test
    void findById_nonexistingNode_cashierObj(){
        int id = -2;

        assertThrows(CashierNotFoundException.class, () -> {
            cashierService.findById(id);
        });
    }

}