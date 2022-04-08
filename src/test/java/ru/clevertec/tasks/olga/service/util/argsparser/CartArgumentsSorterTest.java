//package ru.clevertec.tasks.olga.service.util.argsparser;
//
//import org.junit.jupiter.api.Test;
//import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
//import ru.clevertec.tasks.olga.model.dto.CartParamsDTO;
//import ru.clevertec.tasks.olga.util.argsparser.CartArgumentsSorter;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static ru.clevertec.tasks.olga.util.Constant.*;
//
//class CartArgumentsSorterTest {
//
//    private CartArgumentsSorter sorter =  new CartArgumentsSorter();
//
//    @Test
//    void retrieveArgs_correctCmdArgsForPrint_success() {
//        CartParamsDTO expected = new CartParamsDTO();
//        expected.setAction(ACTION_PRINT);
//        expected.setCard_id(1L);
//        expected.setCashier_id(2L);
//        Map<Long, Integer> goods = new HashMap<>();
//        goods.put(1L, 7);
//        goods.put(2L, 3);
//        expected.setGoods(goods);
//
//        Map<String, String[]> params = new HashMap<>();
//        params.put("action", new String[]{"print"});
//        params.put("products", new String[]{"1-7", "2-3"});
//        params.put("cashier_uid", new String[]{"2"});
//        params.put("card_uid", new String[]{"1"});
//
//        CartParamsDTO actual = sorter.retrieveArgs(params);
//
//        assertEquals(expected, actual);
//
//    }
//
//    @Test
//    void retrieveArgs_correctCmdArgsForFind_success() {
//        CartParamsDTO expected = new CartParamsDTO();
//        expected.setAction(ACTION_FIND_BY_ID);
//        expected.setBill_id(1L);
//
//        Map<String, String[]> params = new HashMap<>();
//        params.put("action", new String[]{"find"});
//        params.put("id", new String[]{"1"});
//
//        CartParamsDTO actual = sorter.retrieveArgs(params);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void retrieveArgs_correctCmdArgsForLog_success() {
//        String path = "D:\\botanstvo\\java\\billPinterNoWeb\\db\\";
//
//        CartParamsDTO expected = new CartParamsDTO();
//        expected.setAction(ACTION_LOG);
//
//        Map<String, String[]> params = new HashMap<>();
//        params.put("action", new String[]{"log"});
//
//        CartParamsDTO actual = sorter.retrieveArgs(params);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void retrieveArgs_incorrectCmdArgsForPrint_exception() {
//        Map<String, String[]> params = new HashMap<>();
//        params.put("action", new String[]{"print"});
//        params.put("products", new String[]{"1-7", "2-3"});
//
//        assertThrows(NoRequiredArgsException.class, () -> {
//            sorter.retrieveArgs(params);
//        });
//
//    }
//
//    @Test
//    void retrieveArgs_incorrectCmdArgsForFind_exception() {
//        Map<String, String[]> params = new HashMap<>();
//        params.put("action", new String[]{"find"});
//
//        assertThrows(NoRequiredArgsException.class, () -> {
//            sorter.retrieveArgs(params);
//        });
//    }
//
//}