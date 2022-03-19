package ru.clevertec.tasks.olga.util.jsonmapper;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;

import java.util.List;

public interface Mapper {
    List<String> PRIMITIVE_TYPE = ArrayListImpl.of(
            "byte", "short", "integer", "long", "float", "double", "boolean"
    );

}
