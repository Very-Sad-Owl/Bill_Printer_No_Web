package ru.clevertec.tasks.olga.repository;

import org.springframework.data.domain.Pageable;
import ru.clevertec.tasks.olga.entity.AbstractModel;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<E extends AbstractModel> {
    long save(E e);
    Optional<E> findById(long id);
    List<E> getAll(Pageable pageable);
    boolean update(E e);
    boolean delete(long id);
}
