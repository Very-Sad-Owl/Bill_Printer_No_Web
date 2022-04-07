package ru.clevertec.tasks.olga.repository;

import ru.clevertec.tasks.olga.entity.AbstractModel;
import ru.clevertec.tasks.olga.exception.repository.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<E extends AbstractModel> {

    long save(E e) throws RepositoryException;
    Optional<E> findById(long id) throws RepositoryException;
    List<E> getAll(int limit, int offset) throws RepositoryException;
    boolean update(E e) throws RepositoryException;
    boolean delete(long id) throws RepositoryException;
}
