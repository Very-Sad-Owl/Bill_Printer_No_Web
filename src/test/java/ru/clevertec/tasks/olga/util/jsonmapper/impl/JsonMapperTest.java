package ru.clevertec.tasks.olga.util.jsonmapper.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.custom_collection.my_list.LinkedListImpl;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.ProductDiscountType;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonMapperTest {

    @Test
    void parseObject_compositeObject_correctJsonString() {
        String expected = "{ \"type\" : \"MORE_THAN_FIVE\", \"i\" : 1, " +
                "\"d\" : 3.5, \"s\" : \"content\", \"arr\" : [ a, b, c ], " +
                "\"list\" : [ a, b, c ], " +
                "\"objList\" : [ 1, \"a\", { \"title\" : null, \"price\" : 0.0, \"discountType\" : null } ], " +
                "\"map\" : { \"1\" : \"a\", \"2\" : \"b\", \"3\" : \"c\" } }";

        String actual = JsonMapper.parseObject(new TestObj());

        assertEquals(expected, actual);
    }

    @Test
    void isWrapper_wrapperType_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isWrapper", Class.class);
        Double arg = 3.4;
        m.setAccessible(true);

        assertTrue((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isWrapper_nonWrapperType_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isWrapper", Class.class);
        String arg = "3.4";
        m.setAccessible(true);

        assertFalse((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isTextOrEnum_string_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isTextOrEnum", Class.class);
        String arg = "text";
        m.setAccessible(true);

        assertTrue((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isTextOrEnum_char_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isTextOrEnum", Class.class);
        Character arg = 'V';
        m.setAccessible(true);

        assertTrue((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isTextOrEnum_enum_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isTextOrEnum", Class.class);
        TestEnum arg = TestEnum.VALUE_TWO;
        m.setAccessible(true);

        assertTrue((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isTextOrEnum_notText_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isTextOrEnum", Class.class);
        Boolean arg = true;
        m.setAccessible(true);

        assertFalse((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isLocalDate_date_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isLocalDate", Class.class);
        LocalDate arg = LocalDate.now();
        m.setAccessible(true);

        assertTrue((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isLocalDate_notDate_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isLocalDate", Class.class);
        String arg = "22.03.2022";
        m.setAccessible(true);

        assertFalse((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isCollection_listImpl_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isCollection", Class.class);
        LinkedListImpl<String> arg = new LinkedListImpl<>();
        m.setAccessible(true);

        assertTrue((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isCollection_map_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isCollection", Class.class);
        Map<Integer, String> arg = new HashMap<>();
        m.setAccessible(true);

        assertFalse((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isMap_list_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isMap", Class.class);
        LinkedListImpl<String> arg = new LinkedListImpl<>();
        m.setAccessible(true);

        assertFalse((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isMap_map_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isMap", Class.class);
        Map<Integer, String> arg = new HashMap<>();
        m.setAccessible(true);

        assertTrue((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isArray_primitiveArray_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isArray", Class.class);
        char[] arg = new char[]{'a', 'b', 'c'};
        m.setAccessible(true);

        assertTrue((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isArray_objectArray_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isArray", Class.class);
        TestObj[] arg = new TestObj[2];
        m.setAccessible(true);

        assertTrue((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    @Test
    void isArray_list_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = JsonMapper.class.getDeclaredMethod("isArray", Class.class);
        List<Double> arg = new ArrayListImpl<>();
        m.setAccessible(true);

        assertFalse((boolean)m.invoke(null, arg.getClass()));
        m.setAccessible(false);
    }

    private enum TestEnum{
        VALUE_ONE, VALUE_TWO;
    }

    private static class TestObj{
        TestEnum testEnum = TestEnum.VALUE_ONE;
        Integer i = 1;
        double d = 3.5;
        String s = "content";
        char[] arr = new char[]{'a', 'b', 'c'};
        List<Character> list = new ArrayListImpl<>();
        List<Object> objList = new ArrayListImpl<>();
        Map<Integer, String> map = new HashMap<>();

        {
            list.add('a');
            list.add('b');
            list.add('c');
            map.put(1, "a");
            map.put(2, "b");
            map.put(3, "c");
            objList.add(1);
            objList.add("a");
            objList.add(new Product());
        }
    }

}