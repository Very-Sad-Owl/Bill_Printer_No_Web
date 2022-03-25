package ru.clevertec.tasks.olga.repository;

import ru.clevertec.tasks.olga.model.AbstractModel;

import java.util.List;

public interface CRUDRepository<E extends AbstractModel> {

    void save(E e, String path);

    E findById(long id, String filePath);

    List<E> getAll(String filePath);

    boolean delete(E e, String filePath);

    E update(E e, String filePath);
}
