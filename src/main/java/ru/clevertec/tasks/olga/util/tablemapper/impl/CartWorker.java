package ru.clevertec.tasks.olga.util.tablemapper.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CartWorker extends NodeWorker<Cart> {

    private final NodeWorker<Cashier> cashierWorker;
    private final NodeWorker<DiscountCard> discountWorker;

    @Autowired
    public CartWorker(NodeWorker<Cashier> cashierWorker, NodeWorker<DiscountCard> discountWorker) {
        this.cashierWorker = cashierWorker;
        this.discountWorker = discountWorker;
    }

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
