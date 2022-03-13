package ru.clevertec.tasks.olga.util.orm.impl;

import by.epam.training.jwd.task03.entity.Attribute;
import by.epam.training.jwd.task03.entity.Node;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.DiscountType;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static ru.clevertec.tasks.olga.util.Constant.*;

@NoArgsConstructor
public class DiscountCardWorker extends NodeWorker<DiscountCard> {

    @Override
    public DiscountCard nodeToModel(Node node) {
        try {
            return new DiscountCard(
                    Long.parseLong(node.getAttrByName("id").getContent()),
                    new SimpleDateFormat("dd.MM.yyyy").parse(node.getAttrByName("birthday").getContent()),
                    DiscountType.valueOf(node.getAttrByName("discount").getContent().toUpperCase())
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Node modelToNode(DiscountCard model) {
        List<Attribute> attributes = new ArrayListImpl<>();
        attributes.add(new Attribute(XML_ID_ATTR, model.getId()+""));
        attributes.add(new Attribute(XML_BDATE_ATTR,
                new SimpleDateFormat("dd.MM.yyyy").format(model.getBirthday())));
        attributes.add(new Attribute(XML_DISCOUNT_ATTR, model.getDiscountType().toString().toUpperCase()));
        return Node.newBuilder()
                .withName(CARD_XML_NAME)
                .withAttributes(attributes)
                .build();
    }

}
