package ru.clevertec.tasks.olga.util.jsonmapper.impl;

import lombok.Data;
import org.junit.jupiter.api.Test;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.model.Product;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonMapperTest {

    JsonMapper mapper = new JsonMapper();
    private TestObj testObj = new TestObj();

    @Test
    void parseObject() {
    }

    @Test
    void parsePrimitiveAndWrappers() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = mapper.getClass().getDeclaredMethod("parsePrimitiveAndWrappers",
                Field.class, Object.class);
        method.setAccessible(true);

        String expected = "\"i\" : 1";

        String actual = (String) method.invoke(mapper, testObj.getClass().getDeclaredField("i"), testObj);

        assertEquals(expected, actual);
    }

    @Test
    void parseArray() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Method method = mapper.getClass().getDeclaredMethod("parseArray",
                Field.class, Object.class);
        method.setAccessible(true);

        String expected = "\"arr\" : [\n" +
                "[a,b,,\n" +
                "]";

        String actual = (String) method.invoke(mapper, testObj.getClass()
                .getDeclaredField("arr"), testObj);

        assertEquals(expected, actual);
    }

    @Test
    void parseMap() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = mapper.getClass().getDeclaredMethod("parseMap",
                Field.class, Object.class);
        method.setAccessible(true);

        String expected = "\"arr\" : [\n" +
                "[a,b,,\n" +
                "]";

        String actual = (String) method.invoke(mapper, testObj.getClass()
                .getDeclaredField("map"), testObj);

        assertEquals(expected, actual);
    }

    @Test
    void parseList() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = mapper.getClass().getDeclaredMethod("parseList",
                Field.class, Object.class);
        method.setAccessible(true);

        String expected = "\"arr\" : [\n" +
                "[a,b,,\n" +
                "]";

        String actual = (String) method.invoke(mapper, testObj.getClass()
                .getDeclaredField("objList"), testObj);

        assertEquals(expected, actual);
    }

    private class TestObj{
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