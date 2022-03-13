package ru.clevertec.tasks.olga.util.orm;

import by.epam.training.jwd.task03.entity.Node;
import ru.clevertec.tasks.olga.model.AbstractModel;

import java.util.List;

public abstract class NodeWorker<E extends AbstractModel> {

    protected WorkerFactory factory = WorkerFactory.getInstance();

    public abstract E nodeToModel(Node node);
    public abstract Node modelToNode(E model);

    public void nodeToList(Node rootNode, List<E> models) {
        if(rootNode.getChildNodes() == null) {
            models.add(nodeToModel(rootNode)
            );
        } else {
            for(Node childNode : rootNode.getChildNodes()) {
                nodeToList(childNode, models);
            }
        }
    }

}
