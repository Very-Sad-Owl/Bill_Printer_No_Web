package ru.clevertec.tasks.olga.repository;

import ru.clevertec.tasks.olga.model.AbstractModel;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<E extends AbstractModel> {

    long save(E e);
    Optional<E> findById(long id);
    List<E> getAll();
    boolean update(E e);
    boolean delete(long id);
}
