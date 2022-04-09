package ru.clevertec.tasks.olga.util.tablemapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.AbstractModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public abstract class ModelRowMapper<T extends AbstractModel> implements RowMapper<T> {
    public abstract void modelToNewNode(T model, PreparedStatement st) throws SQLException;
    public abstract void modelToExisingNode(T model, PreparedStatement st) throws SQLException;

}
