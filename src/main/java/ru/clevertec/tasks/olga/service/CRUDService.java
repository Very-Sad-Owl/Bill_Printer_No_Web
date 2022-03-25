package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.model.AbstractModel;

import java.util.List;

public interface CRUDService<E extends AbstractModel> {

    void save(E e, String fileName);

    E findById(long id, String filePath);

    List<E> getAll(String filePath);

    boolean delete(E e, String filePath);

    E update(E e, String filePath);

}
