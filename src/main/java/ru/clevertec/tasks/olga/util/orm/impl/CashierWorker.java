package ru.clevertec.tasks.olga.util.orm.impl;

import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import lombok.NoArgsConstructor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
public class CashierWorker extends NodeWorker<Cashier> {

    @Override
    public Cashier nodeToModel(ResultSet rs, boolean isJoin) throws SQLException {
        return Cashier.builder()
                .id(rs.getLong(!isJoin ? "id" : "cashier_id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .build();
    }

    @Override
    public void modelToNode(Cashier model, PreparedStatement st) throws SQLException {
        st.setString(1, model.getName());
        st.setString(2, model.getSurname());
    }
}
