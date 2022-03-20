package ru.clevertec.tasks.olga.util.jsonmapper;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;

import java.util.List;

public interface Mapper {
    List<String> WRAPPER_TYPE = ArrayListImpl.of(
            "Byte", "Short", "Integer", "Long", "Float", "Double", "Boolean", "Character"
    );
    String parseObject(Object o);
}
