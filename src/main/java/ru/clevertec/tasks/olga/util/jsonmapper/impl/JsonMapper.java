package ru.clevertec.tasks.olga.util.jsonmapper.impl;

import lombok.SneakyThrows;
import ru.clevertec.tasks.olga.util.jsonmapper.Mapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class JsonMapper implements Mapper {

    private static final String primitivePattern = "\"%s\" : %s";
    private static final String stringPattern = "\"%s\" : \"%s\"";
    private static final String arrayPattern = "\"%s\" : [\n%s\n]";
    private static final String mapOrObjectPattern = "\"%s\" : {\n%s\n}";
    private static final String mapPairPattern = "%s : %s";

    @SneakyThrows
    public String parseObject(Object o) {
        if (o == null) {
            return null;
        }

        StringBuilder result = new StringBuilder("{");
        Class<?> oClass = o.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            result.append(defineFieldType(declaredField, o));
            result.append(",");
            declaredField.setAccessible(false);
        }
        result.deleteCharAt(result.length() - 1);

        return result.append("}").toString();
    }


    private String choseParse(Object o) {
        if (o.getClass().isPrimitive()) {
            return parsePrimitiveAndWrappers(o);
        } else if (o.getClass().isArray()) {
            return parseArray(o);
        } else if (Number.class.isAssignableFrom(o.getClass())
                        || Character.class.isAssignableFrom(o.getClass())) {
            return parsePrimitiveAndWrappers(o);
        } else if (String.class.isAssignableFrom(o.getClass())) {
            return parseString(o);
        } else if (Iterable.class.isAssignableFrom(o.getClass())) {
            return parseList(o);
        } else if (Map.class.isAssignableFrom(o.getClass())) {
            return parseMap(o);
        } else {
            return parseObject(o);
        }
    }

    private String defineFieldType(Field field, Object o) { //TODO: enum
        if (field.getType().isPrimitive()) {
            return parsePrimitiveAndWrappers(field, o);
        } else if (field.getType().isArray()) {
            return parseArray(field, o);
        } else if (Number.class.isAssignableFrom(field.getType())) {
            return parsePrimitiveAndWrappers(field, o);
        } else if (String.class.isAssignableFrom(field.getType())) {
            return parseString(field, o);
        } else if (Iterable.class.isAssignableFrom(field.getType())) {
            return parseList(field, o);
        } else if (Map.class.isAssignableFrom(field.getType())) {
            return parseMap(field, o);
        } else if (field.getType().isEnum()){
            return parseEnum(field, o);
        }else {
            return parseObject(field, o);
        }
    }

    @SneakyThrows
    private String parseEnum(Field field, Object o) {
        Object o1 = field.get(o);
        if (o1 == null) return String.format(mapPairPattern, field.getName(), "null");
        return String.format(stringPattern, field.getName(), choseParse(o1));
    }

    @SneakyThrows
    private String parsePrimitiveAndWrappers(Field field, Object o) {
        String json;
        if (char.class.isAssignableFrom(field.getType())) {
            json = String.format(stringPattern, field.getName(), field.get(o));
        } else {
            json = String.format(primitivePattern, field.getName(), field.get(o));
        }
        return json;
    }

    @SneakyThrows
    private String parseArray(Field field, Object o) {
        Object[] objects = new Object[Array.getLength(field.get(o))];
        StringBuilder content = new StringBuilder("[");
        for (int i = 0; i < objects.length - 1; i++) {
            content.append(Array.get(field.get(o), i));
            content.append(",");
        }
        content.append(",");

        return String.format(arrayPattern, field.getName(), content);
    }

    @SneakyThrows
    private String parseMap(Field field, Object o) {
        Map<?, ?> map = ((Map<?, ?>) field.get(o));
        StringBuilder content = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            content.append(
                    String.format(mapPairPattern,
//                            choseParse(entry.getKey()), choseParse(entry.getValue())
                            entry.getKey(), entry.getValue()
                    )
            );
            content.append(",");
        }
        content.deleteCharAt(map.size() - 1);

        return String.format(mapOrObjectPattern, field.getName(), content);
    }

    @SneakyThrows
    private String parseList(Field field, Object list) {
        StringBuilder res = new StringBuilder();
        List<?> listOfObjects = ((List<?>) field.get(list));
        for (Object o1 : listOfObjects) {
            res.append(choseParse(o1)).append(",");
        }
        res.deleteCharAt(res.length() - 1);
        return String.format(arrayPattern, field.getName(), res);
    }

    @SneakyThrows
    private String parseString(Field field, Object o) {
        Object o1 = field.get(o);
        return String.format(stringPattern, field.getName(), field.get(o));
    }

    @SneakyThrows
    private String parseObject(Field field, Object o) {
        if (o == null) {
            return null;
        }
        Object o1 = field.get(o);
        StringBuilder content = new StringBuilder();
        Class<?> oClass = o1.getClass();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            content.append(defineFieldType(declaredField, o1));
            declaredField.setAccessible(false);
            content.append(",");
        }
        content.deleteCharAt(content.length() - 1);

        return String.format(mapOrObjectPattern, field.getName(), content);
    }

//    public String parseToString(Object o) {
//        if (o == null) {
//            return null;
//        }
//        StringBuilder result = new StringBuilder("{");
//        Class<?> oClass = o.getClass();
//        Field[] declaredFields = oClass.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            declaredField.setAccessible(true);
//
//            result.append(defineFieldType(declaredField, o));
//
//            declaredField.setAccessible(false);
//        }
//
//        return result.deleteCharAt(result.length() - 1).append("}").toString();
//    }

    private String parsePrimitiveAndWrappers(Object o) {
        return o + "";
    }

    private String parseArray(Object o) {
        Object[] array2 = new Object[Array.getLength(o)];
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < array2.length; i++) {
            array2[i] = Array.get(o, i);
        }
        for (Object o1 : array2) {
            res.append(choseParse(o1)).append(",");
        }

        return res.deleteCharAt(res.length() - 1).append("]").toString();
    }

    private String parseMap(Object o) {
        Map<?, ?> map = ((Map<?, ?>) o);
        StringBuilder str = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            str.append(choseParse(entry.getKey())).append(":");
            str.append(choseParse(entry.getValue())).append(",");
        }

        return str.deleteCharAt(str.length() - 1).append("}").toString();
    }

    private String parseList(Object o) {
        StringBuilder res = new StringBuilder("[");
        List<?> list = ((List<?>) (o));
        for (Object o1 : list) {
            res.append("\"").append(choseParse(o1)).append(",");
        }
        return res.deleteCharAt(res.length() - 1).append("]").toString();
    }

    private String parseString(Object o) {
        return "\"" + o + "\"";
    }

}

