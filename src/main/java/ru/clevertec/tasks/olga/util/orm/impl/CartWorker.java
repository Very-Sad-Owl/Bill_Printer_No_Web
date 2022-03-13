package ru.clevertec.tasks.olga.util.orm.impl;

import by.epam.training.jwd.task03.entity.Attribute;
import by.epam.training.jwd.task03.entity.Node;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.model.*;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;

import java.util.ArrayList;
import java.util.List;

import static ru.clevertec.tasks.olga.util.Constant.*;

public class CartWorker extends NodeWorker<Cart> {

    private NodeWorker<Product> productNodeWorker;
    private NodeWorker<Cashier> cashierNodeWorker;
    private NodeWorker<DiscountCard> discountCardNodeWorker;

    public CartWorker(){
        this.productNodeWorker = new ProductWorker();
        this.cashierNodeWorker = new CashierWorker();
        this.discountCardNodeWorker = new DiscountCardWorker();
    }

    @Override
    public Cart nodeToModel(Node node) {
        Cart result = new Cart();
        result.setId(Long.parseLong(node.getAttrByName(XML_ID_ATTR).getContent()));
            for (Node child : node.getChildNodes()){
                switch (child.getName()){
                    case CASHIER_XML_NAME :
                        cashierNodeWorker.nodeToModel(child);
                        result.setCashier(cashierNodeWorker.nodeToModel(child));
                        break;
                    case CARD_XML_NAME:
                        result.setDiscountCard(discountCardNodeWorker.nodeToModel(child));
                        break;
                    case SLOTS_COLLECTION_XML_NAME:
                        for (Node slot : child.getChildNodes()){
                            Slot newSlot = new Slot();
                            newSlot.setQuantity(Integer.parseInt(slot.getAttrByName(XML_QUANTITY_ATTR).getContent()));
                            newSlot.setProduct(productNodeWorker.nodeToModel(slot.getChildNodes().get(0)));
                            result.addSlot(newSlot);
                        }
                        break;
                }
            }
        return result;
    }

    @Override
    public void nodeToList(Node rootNode, List<Cart> models){
        List<Node> nodes = rootNode.getChildNodes();
        for (Node cart : nodes){
            models.add(nodeToModel(cart));
        }
    }

    @Override
    public Node modelToNode(Cart model) {
        List<Attribute> attributes = new ArrayListImpl<>();
        attributes.add(new Attribute(XML_ID_ATTR, model.getId()+""));
        List<Node> children = new ArrayListImpl<>();
        children.add(cashierNodeWorker.modelToNode(model.getCashier()));
        children.add(discountCardNodeWorker.modelToNode(model.getDiscountCard()));
        children.add(formSlots(model.getPositions()));
        Node cart = Node.newBuilder()
                .withName(CART_XML_NAME)
                .withAttributes(attributes)
                .withChildren(children)
                .build();
        List<Node> cartsChildren = new ArrayListImpl<>();
        cartsChildren.add(cart);
        return Node.newBuilder()
                .withName(CART_COLLECTION_XML_NAME)
                .withChildren(cartsChildren)
                .build();
    }

    private Node formSlots(List<Slot> slots){
        List<Node> nodeSlots = new ArrayListImpl<>();
        for (Slot slot : slots){
            List<Attribute> attributes = new ArrayListImpl<>();
            attributes.add(new Attribute(XML_QUANTITY_ATTR, slot.getQuantity()+""));
            List<Node> children = new ArrayListImpl<>();
            children.add(productNodeWorker.modelToNode(slot.getProduct()));
            nodeSlots.add(
                    Node.newBuilder()
                    .withName(SLOT_XML_NAME)
                    .withAttributes(attributes)
                    .withChildren(children)
                    .build()
            );
        }
        return Node.newBuilder()
                .withName(SLOTS_COLLECTION_XML_NAME)
                .withChildren(nodeSlots)
                .build();
    }
}
