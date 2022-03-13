package ru.clevertec.tasks.olga.service.util.argsparser;

import org.junit.jupiter.api.Test;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.model.ParamsDTO;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.clevertec.tasks.olga.util.Constant.*;

class ArgumentsSorterTest {

    private ArgumentsSorter sorter =  new ArgumentsSorter();

    @Test
    void retrieveArgs_correctCmdArgsForPrint_success() {
        ParamsDTO expected = new ParamsDTO();
        expected.setAction(ACTION_PRINT);
        expected.setCard_id(1L);
        expected.setCashier_id(2L);
        Map<Long, Integer> goods = new HashMap<>();
        goods.put(1L, 7);
        goods.put(2L, 3);
        expected.setGoods(goods);
        expected.setDataPath("D:\\botanstvo\\java\\billPinterNoWeb\\db\\");

        String[] params = new String[]
                {"1-7", "2-3", "card_uid-1", "cashier_uid-2", "action-print",
                        "path-D:\\botanstvo\\java\\billPinterNoWeb\\db\\"};
        ParamsDTO actual = sorter.retrieveArgs(params);

        assertEquals(expected, actual);

    }

    @Test
    void retrieveArgs_correctCmdArgsForFind_success() {
        ParamsDTO expected = new ParamsDTO();
        expected.setAction(ACTION_FIND_BY_ID);
        expected.setBill_id(1L);
        expected.setDataPath("D:\\botanstvo\\java\\billPinterNoWeb\\db\\");

        String[] params = new String[]
                {"id-1", "action-find", "path-D:\\botanstvo\\java\\billPinterNoWeb\\db\\"};
        ParamsDTO actual = sorter.retrieveArgs(params);

        assertEquals(expected, actual);
    }

    @Test
    void retrieveArgs_correctCmdArgsForLog_success() {
        String path = "D:\\botanstvo\\java\\billPinterNoWeb\\db\\";

        ParamsDTO expected = new ParamsDTO();
        expected.setAction(ACTION_LOG);
        expected.setDataPath(path);

        String[] params = new String[]
                {"action-log", "path-D:\\botanstvo\\java\\billPinterNoWeb\\db\\"};
        ParamsDTO actual = sorter.retrieveArgs(params);

        assertEquals(expected, actual);
    }

    @Test
    void retrieveArgs_incorrectCmdArgsForPrint_exception() {
        String[] params = new String[]
                {"1-7", "2-3", "cashier_uid-2", "action-print"};

        assertThrows(NoRequiredArgsException.class, () -> {
            sorter.retrieveArgs(params);
        });

    }

    @Test
    void retrieveArgs_incorrectCmdArgsForFind_exception() {
        String[] params = new String[]
                {"action-find"};

        assertThrows(NoRequiredArgsException.class, () -> {
            sorter.retrieveArgs(params);
        });
    }

}