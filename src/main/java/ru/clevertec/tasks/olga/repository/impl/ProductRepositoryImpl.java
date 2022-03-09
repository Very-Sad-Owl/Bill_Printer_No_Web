package ru.clevertec.tasks.olga.repository.impl;

import by.epam.training.jwd.task03.entity.Node;
import by.epam.training.jwd.task03.service.exception.ServiceException;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.ProductNotFoundException;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.repository.models_repo.ProductRepository;
import ru.clevertec.tasks.olga.util.node_converter.NodeWorker;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.List;
import java.util.ResourceBundle;

public class ProductRepositoryImpl extends AbstractRepository implements ProductRepository {

    @Override
    public void save(Product product) {
        
    }

    @Override
    public Product findById(long id) {
        List<Product> nodes = getAll();
        for (Product product : nodes){
            if (product.getId() == id){
                return product;
            }
        }
        throw new ProductNotFoundException("error.product_not_found");
    }

    @Override
    public List<Product> getAll() {
        Node node;
        NodeWorker<Product> worker = workerFactory.getProductWorker();
        List<Product> products = new ArrayListImpl<>();
        try {
            node = nodeTreeBuilder.parseXML(ResourceBundle.getBundle("db").getString("path.product"));
            worker.nodeToList(node, products);
        } catch (ServiceException e) {
            throw new ReadingException(MessageLocaleService.getMessage("error.reading"));
        }
        return products;
    }


}
