package ru.clevertec.tasks.olga.util.orm;

import by.epam.training.jwd.task03.entity.Node;
import ru.clevertec.tasks.olga.model.AbstractModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class NodeWorker<T extends AbstractModel> {
    public abstract T nodeToModel(ResultSet rs, boolean isJoinQuery) throws SQLException;
    public abstract void modelToNode(T model, PreparedStatement st) throws SQLException;

}
