package ru.clevertec.tasks.olga.util.orm.impl;


import by.epam.training.jwd.task03.entity.Attribute;
import by.epam.training.jwd.task03.entity.Node;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.ProductDiscountType;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import lombok.NoArgsConstructor;
import ru.clevertec.tasks.olga.util.Constant;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ProductWorker extends NodeWorker<Product> {

    @Override
    public Product nodeToModel(Node node) {
        return new Product(
                Long.parseLong(node.getAttrByName(Constant.XML_ID_ATTR).getContent()),
                node.getAttrByName(Constant.XML_TITLE_ATTR).getContent(),
                Double.parseDouble(node.getAttrByName(Constant.XML_PRICE_ATTR).getContent()),
                ProductDiscountType.valueOf(node.getAttrByName(Constant.XML_DISCOUNT_ATTR).getContent().toUpperCase())
        );
    }

    @Override
    public Node modelToNode(Product model) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute(Constant.XML_ID_ATTR, model.getId()+""));
        attributes.add(new Attribute(Constant.XML_TITLE_ATTR, model.getTitle()));
        attributes.add(new Attribute(Constant.XML_PRICE_ATTR, model.getPrice()+""));
        attributes.add(new Attribute(Constant.XML_DISCOUNT_ATTR, model.getDiscountType().toString().toLowerCase()));
        return Node.newBuilder()
                .withName(Constant.PRODUCT_XML_NAME)
                .withAttributes(attributes)
                .build();
    }

}
