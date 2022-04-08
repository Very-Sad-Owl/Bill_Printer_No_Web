package ru.clevertec.tasks.olga.util.tablemapper.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CashierMapper extends ModelRowMapper<Cashier> {

    @Override
    public Cashier nodeToModel(ResultSet rs, boolean isJoin) throws SQLException {
        return Cashier.builder()
                .id(rs.getLong(!isJoin ? "id" : "cashier_id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .build();
    }

    @Override
    public void modelToNewNode(Cashier model, PreparedStatement st) throws SQLException {
        st.setString(1, model.getName());
        st.setString(2, model.getSurname());
    }

    @Override
    public void modelToExisingNode(Cashier model, PreparedStatement st) throws SQLException {
        st.setString(1, model.getName());
        st.setString(2, model.getSurname());
        st.setLong(3, model.getId());
    }
}
