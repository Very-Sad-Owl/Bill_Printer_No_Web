package ru.clevertec.tasks.olga.util.orm.impl;

import by.epam.training.jwd.task03.entity.Attribute;
import by.epam.training.jwd.task03.entity.Node;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static ru.clevertec.tasks.olga.util.Constant.*;

@NoArgsConstructor
public class CashierWorker extends NodeWorker<Cashier> {

    @Override
    public Cashier nodeToModel(Node node) {
        return new Cashier(
                Long.parseLong(node.getAttrByName(XML_ID_ATTR).getContent()),
                node.getAttrByName(XML_NAME_ATTR).getContent(),
                node.getAttrByName(XML_SURNAME_ATTR).getContent()
        );
    }

    @Override
    public Node modelToNode(Cashier model) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute(XML_ID_ATTR, model.getId()+""));
        attributes.add(new Attribute(XML_NAME_ATTR, model.getName()));
        attributes.add(new Attribute(XML_SURNAME_ATTR, model.getSurname()));
        return Node.newBuilder()
                .withName(CASHIER_XML_NAME)
                .withAttributes(attributes)
                .build();
    }
}
