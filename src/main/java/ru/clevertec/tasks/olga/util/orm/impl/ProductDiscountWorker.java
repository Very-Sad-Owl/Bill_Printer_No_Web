package ru.clevertec.tasks.olga.util.orm.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.ProductDiscountType;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class ProductDiscountWorker extends NodeWorker<ProductDiscountType> {

    @Override
    public ProductDiscountType nodeToModel(ResultSet rs, boolean isJoinQuery) throws SQLException {
        return ProductDiscountType.builder()
                .id(rs.getLong(!isJoinQuery ? "id" : "discount_id"))
                .requiredMinQuantity(rs.getInt("required_quantity"))
                .discount(rs.getDouble("discount_val"))
                .title(rs.getString("discount_title"))
                .build();
    }

    @Override
    public void modelToNode(ProductDiscountType model, PreparedStatement st) throws SQLException {

    }

}
