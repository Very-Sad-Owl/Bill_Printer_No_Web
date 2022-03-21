package ru.clevertec.tasks.olga.util.orm.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.model.CardDiscountType;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class DiscountTypeWorker extends NodeWorker<CardDiscountType> {

    @Override
    public CardDiscountType nodeToModel(ResultSet rs, boolean isJoinQuery) throws SQLException {
        return CardDiscountType.builder()
                .id(rs.getLong(!isJoinQuery ? "id" : "discount_id"))
                .title(rs.getString("title"))
                .discount(rs.getDouble("discount"))
                .build();
    }

    @Override
    public void modelToNode(CardDiscountType model, PreparedStatement st) throws SQLException {
        st.setLong(1, model.getId());
        st.setString(2, model.getTitle());
        st.setDouble(3, model.getDiscount());
    }
}
