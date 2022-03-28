package ru.clevertec.tasks.olga.util.orm;

import ru.clevertec.tasks.olga.entity.AbstractModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class NodeWorker<T extends AbstractModel> {
    public abstract T nodeToModel(ResultSet rs, boolean isJoinQuery) throws SQLException;
    public abstract void modelToNewNode(T model, PreparedStatement st) throws SQLException;
    public abstract void modelToExisingNode(T model, PreparedStatement st) throws SQLException;

}
