package ru.clevertec.tasks.olga.util.tablemapper;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.AbstractModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public abstract class NodeWorker<T extends AbstractModel> {
    public abstract T nodeToModel(ResultSet rs, boolean isJoinQuery) throws SQLException;
    public abstract void modelToNewNode(T model, PreparedStatement st) throws SQLException;
    public abstract void modelToExisingNode(T model, PreparedStatement st) throws SQLException;

}
