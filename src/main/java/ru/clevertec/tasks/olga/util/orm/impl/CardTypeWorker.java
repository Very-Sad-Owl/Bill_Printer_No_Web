package ru.clevertec.tasks.olga.util.orm.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class CardTypeWorker extends NodeWorker<CardType> {

    @Override
    public CardType nodeToModel(ResultSet rs, boolean isJoinQuery) throws SQLException {
        return CardType.builder()
                .id(rs.getLong(!isJoinQuery ? "id" : "discount_id"))
                .title(rs.getString("title"))
                .discount(rs.getDouble("discount"))
                .build();
    }

    @Override
    public void modelToNewNode(CardType model, PreparedStatement st) throws SQLException {
        st.setString(1, model.getTitle());
        st.setDouble(2, model.getDiscount());
    }

    @Override
    public void modelToExisingNode(CardType model, PreparedStatement st) throws SQLException {
        st.setString(1, model.getTitle());
        st.setDouble(2, model.getDiscount());
        st.setLong(3, model.getId());
    }
}
