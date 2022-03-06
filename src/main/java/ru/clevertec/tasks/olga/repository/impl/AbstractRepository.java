package ru.clevertec.tasks.olga.repository.impl;

import by.epam.training.jwd.task03.service.NodeTreeBuilder;
import by.epam.training.jwd.task03.service.NodeTreeBuilderFactory;
import ru.clevertec.tasks.olga.util.node_converter.WorkerFactory;

public abstract class AbstractRepository {
    protected NodeTreeBuilder nodeTreeBuilder = NodeTreeBuilderFactory.getInstance().getBuilder();
    protected WorkerFactory workerFactory = WorkerFactory.getInstance();
}
