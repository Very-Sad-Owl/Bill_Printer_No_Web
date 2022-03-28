package ru.clevertec.tasks.olga.util.orm.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class DiscountCardWorker extends NodeWorker<DiscountCard> {

    private static final NodeWorker<CardType> discTypeWorker = new CardTypeWorker();

    @Override
    public DiscountCard nodeToModel(ResultSet rs, boolean isJoinQuery) throws SQLException {
        return DiscountCard.builder()
                .id(rs.getLong(!isJoinQuery ? "id" : "card_id"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .cardType(discTypeWorker.nodeToModel(rs, true))
                .build();
    }

    @Override
    public void modelToNewNode(DiscountCard model, PreparedStatement st) throws SQLException {
        st.setDate(1, Date.valueOf(model.getBirthday()));
        st.setLong(2, model.getCardType().getId());
    }

    @Override
    public void modelToExisingNode(DiscountCard model, PreparedStatement st) throws SQLException {
        st.setDate(1, Date.valueOf(model.getBirthday()));
        st.setLong(2, model.getCardType().getId());
        st.setLong(3, model.getId());
    }
}
