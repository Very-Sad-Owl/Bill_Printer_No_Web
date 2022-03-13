package ru.clevertec.tasks.olga.repository.impl;

import by.epam.training.jwd.task03.entity.Node;
import by.epam.training.jwd.task03.service.exception.ServiceException;
import ru.clevertec.tasks.olga.exception.ProductNotFoundException;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ru.clevertec.tasks.olga.util.MessageLocaleService.getMessage;

public class ProductRepositoryImpl extends AbstractRepository implements ProductRepository {

    @Override
    public void save(Product product, String fileName) {
        
    }

    @Override
    public Product findById(long id, String filePath) {
        List<Product> nodes = getAll(filePath);
        for (Product product : nodes){
            if (product.getId() == id){
                return product;
            }
        }
        throw new ProductNotFoundException("error.product_not_found");
    }

    @Override
    public List<Product> getAll(String path) {
        Node node;
        NodeWorker<Product> worker = workerFactory.getProductWorker();
        List<Product> products = new ArrayList<>();
        String fileName = path + ResourceBundle.getBundle("db").getString("path.product");
        try {
            node = nodeTreeBuilder.parseXML(fileName);
            worker.nodeToList(node, products);
        } catch (ServiceException e) {
            throw new ReadingException(MessageLocaleService.getMessage("error.reading"));
        }
        return products;
    }


}
