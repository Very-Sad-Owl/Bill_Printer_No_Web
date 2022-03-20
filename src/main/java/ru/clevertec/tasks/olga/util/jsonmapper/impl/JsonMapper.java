package ru.clevertec.tasks.olga.util.jsonmapper.impl;

import lombok.SneakyThrows;
import ru.clevertec.tasks.olga.util.jsonmapper.Mapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class JsonMapper implements Mapper {

    private static final String primitivePattern = "\"%s\" : %s";
    private static final String stringPattern = "\"%s\" : \"%s\"";
    private static final String arrayPattern = "\"%s\" : [ %s ]";
    private static final String objectPattern = "\"%s\" : %s";
    private static final String mapPattern = "\"%s\" : { %s }";
    private static final String mapOrObjectContentPattern = "{ %s }";
    private static final String mapPairPattern = "\"%s\" : \"%s\"";


    private String parse(Object o) {
        if (o.getClass().isPrimitive() || WRAPPER_TYPE.contains(o.getClass().getSimpleName())) {
            return parsePrimitivesAndWrappers(o);
        } else if (o.getClass().isArray()) {
            return parseArrayContent(o);
        } else if (String.class.isAssignableFrom(o.getClass())) {
            return parseString(o);
        } else if (Collection.class.isAssignableFrom(o.getClass())) {
            return parseListContent(o);
        } else if (Map.class.isAssignableFrom(o.getClass())) {
            return parseMapContent(o);
        } else {
            return parseObject(o);
        }
    }

    private String parse(Field field, Object o) {
        if (field.getType().isPrimitive() || WRAPPER_TYPE.contains(o.getClass().getSimpleName())) {
            return parsePrimitivesAndWrappers(field, o);
        } else if (field.getType().isArray()) {
            return parseArray(field, o);
        } else if (String.class.isAssignableFrom(field.getType())) {
            return parseString(field, o);
        } else if (Collection.class.isAssignableFrom(field.getType())) {
            return parseList(field, o);
        } else if (Map.class.isAssignableFrom(field.getType())) {
            return parseMap(field, o);
        } else if (field.getType().isEnum()){
            return parseEnum(field, o);
        }else {
            return parseObjectField(field, o);
        }
    }

    @SneakyThrows
    private String parseEnum(Field field, Object o) {
        Object o1 = field.get(o);
        if (o1 == null) return String.format(mapPairPattern, field.getName(), parseNull());
        return String.format(stringPattern, field.getName(), parse(o1));
    }

    @SneakyThrows
    private String parsePrimitivesAndWrappers(Field field, Object o) {
        String json;
        if (char.class.isAssignableFrom(field.getType())) {
            json = String.format(stringPattern, field.getName(), field.get(o));
        } else {
            json = String.format(primitivePattern, field.getName(), field.get(o));
        }
        return json;
    }

    private String parsePrimitivesAndWrappers(Object o) {
        return o + "";
    }

    @SneakyThrows
    private String parseArray(Field field, Object o) {
        String content = parseArrayContent(field.get(o));
        return String.format(arrayPattern, field.getName(), content);
    }

    private String parseArrayContent(Object o) {
        if (o == null) return parseNull();
        Object[] objects = new Object[Array.getLength(o)];
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            content.append(Array.get(o, i));
            content.append(",");
        }
        content.deleteCharAt(content.length() - 1);

        return content.toString();
    }

    @SneakyThrows
    private String parseMap(Field field, Object o) {
        String content = parseMapContent(field.get(o));
        return String.format(mapPattern, field.getName(), content);
    }

    private String parseMapContent(Object o) {
        if (o == null) return parseNull();
        Map<?, ?> map = ((Map<?, ?>) o);
        StringBuilder content = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            content.append(
                    String.format(mapPairPattern,
                            entry.getKey(), entry.getValue()
                    )
            );
            content.append(",");
        }
        content.deleteCharAt(content.length() - 1);

        return content.toString();
    }

    @SneakyThrows
    private String parseList(Field field, Object list) {
        String content = parseListContent(field.get(list));
        return String.format(arrayPattern, field.getName(), content);
    }

    private String parseListContent(Object o) {
        if (o == null) return parseNull();
        StringBuilder res = new StringBuilder();
        List<?> listOfObjects = ((List<?>) o);
        for (Object o1 : listOfObjects) {
            res.append(parse(o1)).append(",");
        }
        res.deleteCharAt(res.length() - 1);

        return res.toString();
    }

    @SneakyThrows
    private String parseString(Field field, Object o) {
        Object o1 = field.get(o);
        return String.format(stringPattern, field.getName(), parseString(o1));
    }

    private String parseString(Object o) {
        return o + "";
    }

    @SneakyThrows
    private String parseObjectField(Field field, Object o) {
        if (o == null) {
            return null;
        }
        field.setAccessible(true);
        Object o1 = field.get(o);
        String content = parseObject(o1);
        field.setAccessible(false);
        return String.format(objectPattern, field.getName(), content);
    }

    @SneakyThrows
    public String parseObject(Object o) {
        if (o == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        Class<?> oClass = o.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            result.append(parse(declaredField, o));
            result.append(",");
            declaredField.setAccessible(false);
        }
        result.deleteCharAt(result.length() - 1);

        return String.format(mapOrObjectContentPattern, result);
    }

    private String parseNull(){
        return null + "";
    }
}

