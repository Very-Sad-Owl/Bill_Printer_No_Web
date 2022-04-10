package ru.clevertec.tasks.olga.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.tasks.olga.entity.AbstractModel;
import ru.clevertec.tasks.olga.dto.AbstractDto;

import java.util.List;

public interface CRUDService<E extends AbstractModel, T extends AbstractDto> {
    E save(T e);
    E findById(long id);
    List<E> getAll(Pageable pageable);
    void delete(long id);
    E put(T dto);
    E patch(T dto);
}
