package ru.clevertec.tasks.olga.util.tablemapper.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CardTypeMapper extends ModelRowMapper<CardType> {

    @Override
    public CardType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CardType.builder()
                .id(rs.getLong("discount_id"))
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
