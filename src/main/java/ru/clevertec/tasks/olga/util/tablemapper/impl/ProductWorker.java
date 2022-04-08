package ru.clevertec.tasks.olga.util.tablemapper.impl;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductWorker extends NodeWorker<Product> {

    private final NodeWorker<ProductDiscountType> discountWorker;

    @Autowired
    public ProductWorker(NodeWorker<ProductDiscountType> discountWorker) {
        this.discountWorker = discountWorker;
    }

    @Override
    public Product nodeToModel(ResultSet rs, boolean isJoin) throws SQLException {
        Product found = new Product();
        found.setId(rs.getLong(!isJoin ? "id" : "product_id"));
        found.setTitle(rs.getString("title"));
        found.setDiscountType(discountWorker.nodeToModel(rs, true));
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
