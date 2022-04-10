package ru.clevertec.tasks.olga.util.tablemapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BillMapper extends ModelRowMapper<Cart> {

    private final ModelRowMapper<Cashier> cashierWorker;
    private final ModelRowMapper<DiscountCard> discountWorker;

    @Autowired
    public BillMapper(ModelRowMapper<Cashier> cashierWorker, ModelRowMapper<DiscountCard> discountWorker) {
        this.cashierWorker = cashierWorker;
        this.discountWorker = discountWorker;
    }

    @Override
    public void modelToNewNode(Cart model, PreparedStatement st) throws SQLException {
        st.setLong(1, model.getDiscountCard().getId());
        st.setLong(2, model.getCashier().getId());
        st.setDouble(3, model.getPrice());
    }

    @Override
    public void modelToExisingNode(Cart model, PreparedStatement st) throws SQLException {
        st.setLong(1, model.getDiscountCard().getId());
        st.setLong(2, model.getCashier().getId());
        st.setDouble(3, model.getPrice());
        st.setLong(4, model.getId());
    }

    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Cart.builder()
                .id(rs.getLong("bill_id"))
                .cashier(cashierWorker.mapRow(rs, rowNum))
                .discountCard(discountWorker.mapRow(rs, rowNum))
                .price(rs.getDouble("price"))
                .build();
    }
}
