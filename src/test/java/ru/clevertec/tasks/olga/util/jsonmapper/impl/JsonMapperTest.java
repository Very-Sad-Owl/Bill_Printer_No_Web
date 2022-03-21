package ru.clevertec.tasks.olga.util.jsonmapper.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.DiscountType;
import ru.clevertec.tasks.olga.model.Product;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonMapperTest {

    private JsonMapper mapper = new JsonMapper();
    private TestObj testObj = new TestObj();
    private Cart sample = new Cart();

    @Test
    void parseObject() {
        String expected = "{ \"positions\" : [ null ],\"discountCard\" : null,\"cashier\" : null,\"price\" : 0.0 }";

        String actual = mapper.parseObject(sample);

        assertEquals(expected, actual);
    }

    @Test
    void parsePrimitiveAndWrappers() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = mapper.getClass().getDeclaredMethod("parsePrimitivesAndWrappers",
                Field.class, Object.class);
        method.setAccessible(true);

        String expected = "\"i\" : 1";

        String actual = (String) method.invoke(mapper, testObj.getClass().getDeclaredField("i"), testObj);
        method.setAccessible(false);

        assertEquals(expected, actual);
    }

    @Test
    void parseArray() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Method method = mapper.getClass().getDeclaredMethod("parseArray",
                Field.class, Object.class);
        method.setAccessible(true);

        String expected = "\"arr\" : [ a,b,c ]";

        String actual = (String) method.invoke(mapper, testObj.getClass()
                .getDeclaredField("arr"), testObj);
        method.setAccessible(false);

        assertEquals(expected, actual);
    }

    @Test
    void parseMap() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = mapper.getClass().getDeclaredMethod("parseMap",
                Field.class, Object.class);
        method.setAccessible(true);

        String expected = "\"map\" : { \"1\" : \"a\",\"2\" : \"b\",\"3\" : \"c\" }";

        String actual = (String) method.invoke(mapper, testObj.getClass()
                .getDeclaredField("map"), testObj);
        method.setAccessible(false);

        assertEquals(expected, actual);
    }

    @Test
    void parseList() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = mapper.getClass().getDeclaredMethod("parseList",
                Field.class, Object.class);
        method.setAccessible(true);

        String expected = "\"objList\" : [ 1,a,{ \"title\" : \"null\",\"price\" : 0.0,\"discountType\" : \"null\" } ]";

        String actual = (String) method.invoke(mapper, testObj.getClass()
                .getDeclaredField("objList"), testObj);
        method.setAccessible(false);

        assertEquals(expected, actual);
    }

    @Test
    void parseDateObj() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = mapper.getClass().getDeclaredMethod("parseDate",
                Object.class);
        method.setAccessible(true);

        String expected = "21-3-2022";

        String actual = (String) method.invoke(mapper, LocalDate.now());
        method.setAccessible(false);

        assertEquals(expected, actual);
    }

    @Test
    void parseDateField() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Method method = mapper.getClass().getDeclaredMethod("parseDate",
                Field.class, Object.class);
        method.setAccessible(true);

        DiscountCard card = DiscountCard.builder()
                .birthday(LocalDate.now())
                .id(1)
                .discountType(DiscountType.BRONZE)
                .build();
        String expected = "\"birthday\" : \"21-3-2022\"";

        String actual = (String) method.invoke(mapper, card.getClass()
                .getDeclaredField("birthday"), card);
        method.setAccessible(false);

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