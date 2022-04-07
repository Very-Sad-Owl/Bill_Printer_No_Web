package ru.clevertec.tasks.olga.util.validation;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ReflectionFieldsChecker {

    public static boolean inNullOrDefault(Object o) {
        if (o == null) {
            return false;
        } else if (TypeIdentifier.isTextOrEnum(o.getClass())) {
            return !o.equals("");
        } else if (TypeIdentifier.isCollection(o.getClass())) {
            return !((Collection<?>)o).contains(null);
        } else if (TypeIdentifier.isMap(o.getClass())) {
            return !((Map<?, ?>)o).containsKey(Defaults.defaultValue(Long.TYPE))
                    && !((Map<?, ?>)o).containsValue(Defaults.defaultValue(Integer.TYPE));
        } else if (TypeIdentifier.isArray(o.getClass())) {
            return !Arrays.asList(o).contains(null);
        } else if (TypeIdentifier.isWrapper(o.getClass())){
            return o != Defaults.defaultValue(Double.TYPE);
        }
        return true;
    }


    /** util inner classes **/

    private static final class TypeIdentifier {

        private static boolean isWrapper(Class<?> type) {
            return ClassUtils.isPrimitiveOrWrapper(type);
        }

        private static boolean isTextOrEnum(Class<?> type) {
            return type.isEnum()
                    || String.class.isAssignableFrom(type)
                    || Character.class.isAssignableFrom(type);
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

}
