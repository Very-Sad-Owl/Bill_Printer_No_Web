package ru.clevertec.tasks.olga.service.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.tasks.olga.exception.CashierNotFoundException;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.service.CashierService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashierServiceImplTest {

    private static final String DB_PATH = System.getProperty("user.dir")+"\\db\\test_db\\";
    private CashierService cashierService = new CashierServiceImpl();

    @Test
    void getAll(){
        List<Cashier> expected = new ArrayList<>();
        expected.add(new Cashier(1, "Oleg", "Geosimp"));
        expected.add(new Cashier(2, "Olga", "Mailychko"));

        List<Cashier> actual = cashierService.getAll(DB_PATH);

        assertEquals(expected, actual);
    }

    @Test
    void findById_existingNode_cashierObj(){
        int id = 2;

        Cashier excpected = new Cashier(id, "Olga", "Mailychko");

        Cashier actual = cashierService.findById(id, DB_PATH);

        assertEquals(excpected, actual);
    }

    @Test
    void findById_nonexistingNode_cashierObj(){
        int id = -2;

        assertThrows(CashierNotFoundException.class, () -> {
            cashierService.findById(id, DB_PATH);
        });
    }

}