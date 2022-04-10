package ru.clevertec.tasks.olga.util.tablemapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CardMapper extends ModelRowMapper<DiscountCard> {

    private final ModelRowMapper<CardType> discTypeWorker;

    @Autowired
    public CardMapper(ModelRowMapper<CardType> discTypeWorker) {
        this.discTypeWorker = discTypeWorker;
    }

    @Override
    public DiscountCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DiscountCard.builder()
                .id(rs.getLong("card_id"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .cardType(discTypeWorker.mapRow(rs, rowNum))
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
