package ru.clevertec.tasks.olga.util.orm.impl;

import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.Slot;
import ru.clevertec.tasks.olga.model.dto.SlotDto;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SlotWorker extends NodeWorker<Slot> {

    private static final NodeWorker<Product> productWorker = new ProductWorker();

    @Override
    public Slot nodeToModel(ResultSet rs, boolean isJoin) throws SQLException {
        return Slot.builder()
                .id(rs.getLong(!isJoin ? "id" : "slot_id"))
                .product(productWorker.nodeToModel(rs, true))
                .quantity(rs.getInt("quantity"))
                .totalPrice(rs.getDouble("price"))
                .build();
    }

    @Override
    public void modelToNode(Slot model, PreparedStatement st) throws SQLException {
        st.setLong(1, model.getProduct().getId());
        st.setInt(2, model.getQuantity());
        st.setDouble(3, model.getTotalPrice());
    }
}
