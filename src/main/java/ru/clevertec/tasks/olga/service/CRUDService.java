package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.model.AbstractModel;

import java.util.List;

public interface CRUDService<E extends AbstractModel> {

    void save(E e);

    E findById(long id);

    List<E> getAll();


}
