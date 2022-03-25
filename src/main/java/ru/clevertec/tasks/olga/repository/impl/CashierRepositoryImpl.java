package ru.clevertec.tasks.olga.repository.impl;

import by.epam.training.jwd.task03.entity.Node;
import by.epam.training.jwd.task03.service.exception.ServiceException;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.annotation.UseCache;
import ru.clevertec.tasks.olga.exception.CashierNotFoundException;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.repository.CashierRepository;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import ru.clevertec.tasks.olga.util.MessageLocaleService;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CashierRepositoryImpl extends AbstractRepository implements CashierRepository {

    @UseCache
    @Override
    public void save(Cashier cashier, String fileName) {

    }

    @UseCache
    @Override
    public Cashier findById(long id, String path) {
        List<Cashier> nodes = getAll(path);
        for (Cashier product : nodes){
            if (product.getId() == id){
                return product;
            }
        }
        throw new CashierNotFoundException(MessageLocaleService.getMessage("error.cashier_not_found"));
    }

    @Override
    public List<Cashier> getAll(String path) {
        Node node;
        NodeWorker<Cashier> worker = workerFactory.getCashierWorker();
        List<Cashier> products = new ArrayListImpl<>();
        String fileName = path + ResourceBundle.getBundle("db").getString("path.cashier");
        try {
            node = nodeTreeBuilder.parseXML(fileName);
            worker.nodeToList(node, products);
        } catch (ServiceException e) {
            throw new ReadingException("error.reading");
        }
        return products;
    }

    @UseCache
    @Override
    public boolean delete(Cashier cashier, String filePath) {
        return false;
    }

    @UseCache
    @Override
    public Cashier update(Cashier cashier, String filePath) {
        return null;
    }
}
