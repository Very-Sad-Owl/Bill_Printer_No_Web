package ru.clevertec.tasks.olga.util.tablemapper.impl;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductDiscountMapper extends ModelRowMapper<ProductDiscountType> {

    @Override
    public ProductDiscountType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ProductDiscountType.builder()
                .id(rs.getLong("discount_id"))
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
