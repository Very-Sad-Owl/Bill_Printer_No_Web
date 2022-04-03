package ru.clevertec.tasks.olga.util.tablemapper.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
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
    public void modelToNewNode(ProductDiscountType model, PreparedStatement st) throws SQLException {
        st.setString(1, model.getTitle());
        st.setDouble(2, model.getDiscount());
        st.setInt(3, model.getRequiredMinQuantity());
    }

    @Override
    public void modelToExisingNode(ProductDiscountType model, PreparedStatement st) throws SQLException {
        st.setString(1, model.getTitle());
        st.setDouble(2, model.getDiscount());
        st.setInt(3, model.getRequiredMinQuantity());
        st.setLong(4, model.getId());
    }
}
