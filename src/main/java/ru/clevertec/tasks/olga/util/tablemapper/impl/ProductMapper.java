package ru.clevertec.tasks.olga.util.tablemapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductMapper extends ModelRowMapper<Product> {

    private final ModelRowMapper<ProductDiscountType> discountWorker;

    @Autowired
    public ProductMapper(ModelRowMapper<ProductDiscountType> discountWorker) {
        this.discountWorker = discountWorker;
    }

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product found = new Product();
        found.setId(rs.getLong("product_id"));
        found.setTitle(rs.getString("title"));
        found.setDiscountType(discountWorker.mapRow(rs, rowNum));
        found.setPrice(rs.getDouble("price"));
        return found;
    }

    @Override
    public void modelToNewNode(Product product, PreparedStatement st) throws SQLException {
        st.setString(1, product.getTitle());
        st.setDouble(2, product.getPrice());
        st.setLong(3, product.getDiscountType().getId());
    }

    @Override
    public void modelToExisingNode(Product product, PreparedStatement st) throws SQLException {
        st.setString(1, product.getTitle());
        st.setDouble(2, product.getPrice());
        st.setLong(3, product.getDiscountType().getId());
        st.setLong(4, product.getId());
    }
}
