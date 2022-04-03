package ru.clevertec.tasks.olga.util.tablemapper.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DiscountCardWorker extends NodeWorker<DiscountCard> {

    private final NodeWorker<CardType> discTypeWorker;

    @Autowired
    public DiscountCardWorker(NodeWorker<CardType> discTypeWorker) {
        this.discTypeWorker = discTypeWorker;
    }

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
