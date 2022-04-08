package ru.clevertec.tasks.olga.util.validation;

import lombok.SneakyThrows;
import ru.clevertec.tasks.olga.dto.AbstractDto;
import ru.clevertec.tasks.olga.exception.crud.InvalidArgException;
import ru.clevertec.tasks.olga.exception.crud.NoRequiredArgsException;

import java.lang.reflect.Field;

public class CRUDParamsValidator {

    @SneakyThrows
    public static void validateId(long id) {
        if (id <= 0) throw new InvalidArgException(id + "");
    }

    @SneakyThrows
    public static void validateFullyFilledDto(AbstractDto dto) {
        Class<?> current = dto.getClass();
        Field[] declaredFields = current.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object fieldValue = field.get(dto);
            if (ReflectionFieldsChecker.isNullOrDefault(fieldValue) && !field.getName().equals("id"))
                throw new NoRequiredArgsException(field.getName());
        }
    }

    @SneakyThrows
    public static void validatePartlyFilledObject(AbstractDto dto) {
        if (dto.id <= 0) throw new InvalidArgException(dto.id + "");
    }
}
