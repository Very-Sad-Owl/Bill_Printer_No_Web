package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.model.AbstractModel;
import ru.clevertec.tasks.olga.repository.CRUDRepository;
import ru.clevertec.tasks.olga.repository.factory.RepositoryFactory;
import ru.clevertec.tasks.olga.service.CRUDService;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;

public abstract class AbstractService<E extends AbstractModel, R extends CRUDRepository<E>> implements CRUDService<E> {

    private R repositoy;

    protected final ServiceFactory serviceFactory = ServiceFactory.getInstance();

}
