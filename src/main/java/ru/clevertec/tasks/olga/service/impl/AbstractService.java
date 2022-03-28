package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.entity.AbstractModel;
import ru.clevertec.tasks.olga.dto.AbstractDto;
import ru.clevertec.tasks.olga.repository.CRUDRepository;
import ru.clevertec.tasks.olga.repository.factory.RepositoryFactory;
import ru.clevertec.tasks.olga.service.CRUDService;

public abstract class AbstractService<E extends AbstractModel, T extends AbstractDto, R extends CRUDRepository<E>> implements CRUDService<E, T> {

    private R repositoy;

    protected static final RepositoryFactory repoFactory = RepositoryFactory.getInstance();

}
