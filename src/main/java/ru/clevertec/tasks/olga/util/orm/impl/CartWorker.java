package ru.clevertec.tasks.olga.util.orm.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class CartWorker extends NodeWorker<Cart> {

    private static final NodeWorker<Cashier> cashierWorker = new CashierWorker();
    private static final NodeWorker<DiscountCard> discountWorker = new DiscountCardWorker();

    @Override
    public Cart nodeToModel(ResultSet rs, boolean isJoinQuery) throws SQLException {
        return Cart.builder()
                .id(rs.getLong(!isJoinQuery ? "id" : "cart_id"))
                .cashier(cashierWorker.nodeToModel(rs, true))
                .discountCard(discountWorker.nodeToModel(rs, true))
                .price(rs.getDouble("price"))
                .build();
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
}
