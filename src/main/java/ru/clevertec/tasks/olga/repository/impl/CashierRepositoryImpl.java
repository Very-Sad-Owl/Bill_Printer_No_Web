package ru.clevertec.tasks.olga.repository.impl;

import by.epam.training.jwd.task03.entity.Node;
import by.epam.training.jwd.task03.service.exception.ServiceException;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.CashierNotFoundException;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.repository.models_repo.CashierRepository;
import ru.clevertec.tasks.olga.util.node_converter.NodeWorker;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ru.clevertec.tasks.olga.util.MessageLocaleService.getMessage;

public class CashierRepositoryImpl extends AbstractRepository implements CashierRepository {

    @Override
    public void save(Cashier cashier) {

    }

    @Override
    public Cashier findById(long id) {
        List<Cashier> nodes = getAll();
        for (Cashier product : nodes){
            if (product.getId() == id){
                return product;
            }
        }
        throw new CashierNotFoundException(MessageLocaleService.getMessage("error.cashier_not_found"));
    }

    @Override
    public List<Cashier> getAll() {
        Node node;
        NodeWorker<Cashier> worker = workerFactory.getCashierWorker();
        List<Cashier> products = new ArrayListImpl<>();
        try {
            node = nodeTreeBuilder.parseXML(ResourceBundle.getBundle("db").getString("path.cashier"));
            worker.nodeToList(node, products);
        } catch (ServiceException e) {
            throw new ReadingException("");
        }
        return products;
    }
}
