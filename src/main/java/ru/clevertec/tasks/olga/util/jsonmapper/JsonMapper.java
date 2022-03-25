package ru.clevertec.tasks.olga.util.jsonmapper;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;

public class JsonMapper {

    private static final String PAIR = "\"%s\" : %s";
    private static final String ARRAY_OR_LIST = "[ %s ]";
    private static final String MAP_OR_OBJECT = "{ %s }";
    private static final String DATE = "[ %d,%d,%d ]";
    public static final String TEXT = "\"%s\"";
    public static final String DELIMITER = ", ";

    public static String parseObject(Object o) {
        if (TypeIdentifier.isNull(o)) {
            return parseNull();
        } else if (TypeIdentifier.isWrapper(o.getClass())) {
            return wrapperToString(o);
        } else if (TypeIdentifier.isTextOrEnum(o.getClass())) {
            return stringAndEnumToString(o);
        } else if (TypeIdentifier.isLocalDate(o.getClass())) {
            return localDateToString(o);
        } else if (TypeIdentifier.isCollection(o.getClass())) {
            return collectionToString(o);
        } else if (TypeIdentifier.isMap(o.getClass())) {
            return mapToString(o);
        } else if (TypeIdentifier.isArray(o.getClass())) {
            return arrayToString(o);
        } else return objectToJsonString(o);
    }

    private static String parseNull() {
        return null + "";
    }

    private static String wrapperToString(Object o) {
        return o.toString();
    }

    private static String stringAndEnumToString(Object o) {
        return JsonFormatter.textContentToJsonString(o.toString());
    }

    private static String localDateToString(Object o) {
        LocalDate localDate = (LocalDate) o;
        return String.format(DATE,
                localDate.getYear(),
                localDate.getMonthValue(),
                localDate.getDayOfMonth());
    }

    private static String collectionToString(Object object) {
        List<String> jsonString = new ArrayListImpl<>();
        for (Object o : (Iterable<?>) object) {
            jsonString.add(parseObject(o));
        }
        return JsonFormatter.arrayOrListToJsonString(String.join(DELIMITER, jsonString));
    }

    private static String mapToString(Object object) {
        List<String> jsonString = new ArrayListImpl<>();
        for (Map.Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()) {
            jsonString.add(
                    String.format(
                            PAIR,
                            entry.getKey(), parseObject(entry.getValue())
                    )
            );
        }
        return JsonFormatter.mapOrObjectToJsonString(String.join(DELIMITER, jsonString));
    }

    private static String arrayToString(Object o) {
        List<Object> jsonString = new ArrayListImpl<>();
        String typeName = o.getClass().getTypeName();
        if (typeName.equals(byte[].class.getTypeName())) {
            for (byte b : (byte[]) o) {
                jsonString.add(b);
            }
        } else if (typeName.equals(short[].class.getTypeName())) {
            for (short b : (short[]) o) {
                jsonString.add(b);
            }
        } else if (typeName.equals(int[].class.getTypeName())) {
            for (int b : (int[]) o) {
                jsonString.add(b);
            }
        } else if (typeName.equals(long[].class.getTypeName())) {
            for (long b : (long[]) o) {
                jsonString.add(b);
            }
        } else if (typeName.equals(float[].class.getTypeName())) {
            for (float b : (float[]) o) {
                jsonString.add(b);
            }
        } else if (typeName.equals(double[].class.getTypeName())) {
            for (double b : (double[]) o) {
                jsonString.add(b);
            }
        } else if (typeName.equals(char[].class.getTypeName())) {
            for (char b : (char[]) o) {
                jsonString.add(b);
            }
        } else if (typeName.equals(boolean[].class.getTypeName())) {
            for (boolean b : (boolean[]) o) {
                jsonString.add(b);
            }
        } else {
            jsonString.addAll(Arrays.asList((Object[]) o));
        }
        return parseObject(jsonString);
    }

    /** for composite objects **/
    @SneakyThrows
    private static String objectToJsonString(Object o) {
        List<String> jsonString = new ArrayListImpl<>();
        for (Field field : o.getClass().getDeclaredFields()) {
            if (!field.isSynthetic()) {
                field.setAccessible(true);
                jsonString.add(String.format(PAIR, field.getName(), parseObject(field.get(o))));
                field.setAccessible(false);
            }
        }
        return String.format(MAP_OR_OBJECT, String.join(DELIMITER, jsonString));
    }

    /** util inner classes **/

    private static final class TypeIdentifier {

        private static boolean isNull(Object o) {
            return o == null;
        }

        private static boolean isWrapper(Class<?> type) {
            return ClassUtils.isPrimitiveOrWrapper(type);
        }

        private static boolean isTextOrEnum(Class<?> type) {
            return type.isEnum()
                    || String.class.isAssignableFrom(type)
                    || Character.class.isAssignableFrom(type);
        }

        private static boolean isLocalDate(Class<?> type) {
            return type == LocalDate.class;
        }

        private static boolean isCollection(Class<?> type) {
            for (Class<?> typeInterface : type.getInterfaces()) {
                if (List.class.isAssignableFrom(typeInterface)) {
                    return true;
                }
            }
            return false;
        }

        private static boolean isMap(Class<?> type) {
            for (Class<?> typeInterface : type.getInterfaces()) {
                if (typeInterface == Map.class) {
                    return true;
                }
            }
            return false;
        }

        private static boolean isArray(Class<?> type) {
            return type.isArray();
        }

    }

    private static final class JsonFormatter {

        private static String textContentToJsonString(String string) {
            return String.format(TEXT, string);
        }

        private static String arrayOrListToJsonString(String string) {
            return String.format(ARRAY_OR_LIST, string);
        }

        private static String mapOrObjectToJsonString(String string) {
            return String.format(MAP_OR_OBJECT, string);
        }
    }

}
