package ru.clevertec.tasks.olga.repository;

import ru.clevertec.tasks.olga.model.AbstractModel;

import java.util.List;

public interface CRUDRepository<E extends AbstractModel> {

    void save(E e);

    E findById(long id);

    List<E> getAll();
}
