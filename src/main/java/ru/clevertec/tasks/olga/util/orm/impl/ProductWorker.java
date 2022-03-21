package ru.clevertec.tasks.olga.util.orm.impl;

import lombok.NoArgsConstructor;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.ProductDiscountType;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
public class ProductWorker extends NodeWorker<Product> {

    private static final NodeWorker<ProductDiscountType> discountWorker = new ProductDiscountWorker();

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
    public void modelToNode(Product product, PreparedStatement st) throws SQLException {
        st.setString(1, product.getTitle());
        st.setDouble(2, product.getPrice());
        st.setLong(3, product.getDiscountType().getId());
    }

}
