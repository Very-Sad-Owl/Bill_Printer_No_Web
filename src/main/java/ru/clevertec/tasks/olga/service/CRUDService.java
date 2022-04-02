package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.entity.AbstractModel;
import ru.clevertec.tasks.olga.dto.AbstractDto;

import java.util.List;

public interface CRUDService<E extends AbstractModel, T extends AbstractDto> {

    E save(T e);

    E findById(long id);

    List<E> getAll(int limit, int offset);

    boolean delete(long id);

    E update(T dto);

}
