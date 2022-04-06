package ru.clevertec.tasks.olga.repository;

import ru.clevertec.tasks.olga.entity.AbstractModel;
import ru.clevertec.tasks.olga.exception.repoexc.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<E extends AbstractModel> {

    long save(E e);
    Optional<E> findById(long id);
    List<E> getAll(int limit, int offset);
    boolean update(E e);
    boolean delete(long id);
}
