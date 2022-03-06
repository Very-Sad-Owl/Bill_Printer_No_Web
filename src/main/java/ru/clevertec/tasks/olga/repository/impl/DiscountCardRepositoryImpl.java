package ru.clevertec.tasks.olga.repository.impl;

import by.epam.training.jwd.task03.entity.Node;
import by.epam.training.jwd.task03.service.exception.ServiceException;
import ru.clevertec.tasks.olga.exception.CardNotFoundException;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.repository.models_repo.DiscountRepository;
import ru.clevertec.tasks.olga.util.node_converter.NodeWorker;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ru.clevertec.tasks.olga.util.MessageLocaleService.getMessage;

public class DiscountCardRepositoryImpl extends AbstractRepository implements DiscountRepository {

    @Override
    public void save(DiscountCard discountCard) {

    }

    @Override
    public DiscountCard findById(long id) {
        List<DiscountCard> nodes = getAll();
        for (DiscountCard product : nodes){
            if (product.getId() == id){
                return product;
            }
        }
        throw new CardNotFoundException(MessageLocaleService.getMessage("error.card_not_found"));
    }

    @Override
    public List<DiscountCard> getAll() {
        Node node;
        NodeWorker<DiscountCard> worker = workerFactory.getDiscountWorker();
        List<DiscountCard> products = new ArrayList<>();
        try {
            node = nodeTreeBuilder.parseXML(ResourceBundle.getBundle("db").getString("path.card"));
            worker.nodeToList(node, products);
        } catch (ServiceException e) {
            throw new ReadingException(MessageLocaleService.getMessage("error.reading"));
        }
        return products;
    }
}
