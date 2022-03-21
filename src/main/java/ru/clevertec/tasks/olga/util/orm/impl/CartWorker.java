package ru.clevertec.tasks.olga.util.orm.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.model.*;
import ru.clevertec.tasks.olga.model.dto.CartDto;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

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
    public void modelToNode(Cart model, PreparedStatement st) throws SQLException {
        st.setLong(1, model.getDiscountCard().getId());
        st.setLong(2, model.getCashier().getId());
        st.setDouble(3, model.getPrice());
    }

}
