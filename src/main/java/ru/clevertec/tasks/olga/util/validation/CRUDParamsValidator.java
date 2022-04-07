package ru.clevertec.tasks.olga.util.validation;

import lombok.SneakyThrows;
import ru.clevertec.tasks.olga.dto.AbstractDto;
import ru.clevertec.tasks.olga.exception.handeled.InvalidArgExceptionHandled;
import ru.clevertec.tasks.olga.exception.handeled.NoRequiredArgsExceptionHandled;
import ru.clevertec.tasks.olga.exception.handeled.UndefinedExceptionHandled;

import java.lang.reflect.Field;

public class CRUDParamsValidator {

    @SneakyThrows
    public static void validateId(long id) {
        if (id <= 0) throw new InvalidArgExceptionHandled(id + "");
    }

    @SneakyThrows
    public static void validateDtoForSave(AbstractDto dto) {
        try {
            Class<?> current = dto.getClass();
            Field[] declaredFields = current.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object fieldValue = field.get(dto);
                if (!ReflectionFieldsChecker.inNullOrDefault(fieldValue) && !field.getName().equals("id"))
                    throw new NoRequiredArgsExceptionHandled();
            }
        } catch (Exception e) {
            throw new UndefinedExceptionHandled();
        }
    }

    @SneakyThrows
    public static void validatePartlyFilledObject(AbstractDto dto) {
        if (dto.id <= 0) throw new InvalidArgExceptionHandled();
    }
}
