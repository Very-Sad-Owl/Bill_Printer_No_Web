package ru.clevertec.tasks.olga.repository.impl;

import by.epam.training.jwd.task03.entity.Node;
import by.epam.training.jwd.task03.service.exception.ServiceException;
import ru.clevertec.tasks.olga.exception.CartNotFoundException;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.repository.models_repo.CartRepository;
import ru.clevertec.tasks.olga.util.formatter.PseudographicBillFormatter;
import ru.clevertec.tasks.olga.util.formatter.AbstractBillFormatter;
import ru.clevertec.tasks.olga.util.node_converter.NodeWorker;
import ru.clevertec.tasks.olga.util.node_converter.impl.CartWorker;
import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ru.clevertec.tasks.olga.util.MessageLocaleService.getMessage;

@AllArgsConstructor
public class CartRepositoryImpl extends AbstractRepository implements CartRepository {

    private AbstractBillFormatter formatter;

    public CartRepositoryImpl(){
        this.formatter = new PseudographicBillFormatter();
    }


    @Override
    public void save(Cart cart) {
        NodeWorker<Cart> worker = workerFactory.getCartWorker();
        Node cartNode = worker.modelToNode(cart);
        try {
            boolean isAppend = !nodeTreeBuilder
                    .checkFileEmpty(ResourceBundle.getBundle("db").getString("path.bill_log_absolute"));
            if (isAppend){
                cartNode = cartNode.getChildNodes().get(0);
            }
            nodeTreeBuilder.writeXML(ResourceBundle.getBundle("db").getString("path.bill_log_absolute"),
                    cartNode, isAppend);
        } catch (ServiceException e) {
            throw new WritingException(MessageLocaleService.getMessage("error.writing"));
        }
    }


    @Override
    public Cart findById(long id){
        List<Cart> carts = getAll();
        for (Cart cart : carts){
            if (cart.getId() == id){
                return cart;
            }
        }
        throw new CartNotFoundException(MessageLocaleService.getMessage("error.bill_not_found"));
    }

    @Override
    public List<Cart> getAll(){
        Node node;
        CartWorker worker = (CartWorker) workerFactory.getCartWorker();
        List<Cart> log = new ArrayList<>();
        try {
            node = nodeTreeBuilder.parseXML(ResourceBundle.getBundle("db").getString("path.bill_log"));
            worker.nodeToList(node, log);
        } catch (ServiceException e) {
            throw new ReadingException(MessageLocaleService.getMessage("error.reading"));
        }
        return log;
    }

    @Override
    public void setFormatter(AbstractBillFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void printBill(Cart cart, String path) {
        File file = new File(path);
        try (
                FileWriter fwriter = new FileWriter(file, false);
                BufferedWriter bfwriter = new BufferedWriter(fwriter)
             ){
            List<String> content = formatter.format(cart);

            for (String line : content){
                bfwriter.write(line);
                bfwriter.newLine();
            }
            bfwriter.flush();
        } catch (IOException e) {
            throw new WritingException(MessageLocaleService.getMessage("error.writing"));
        }
    }
}
