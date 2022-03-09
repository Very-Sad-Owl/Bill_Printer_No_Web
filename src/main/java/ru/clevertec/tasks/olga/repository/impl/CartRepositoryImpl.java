package ru.clevertec.tasks.olga.repository.impl;

import by.epam.training.jwd.task03.entity.Node;
import by.epam.training.jwd.task03.service.exception.ServiceException;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.CartNotFoundException;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.repository.CartRepository;
import ru.clevertec.tasks.olga.util.formatter.PseudographicBillFormatter;
import ru.clevertec.tasks.olga.util.formatter.AbstractBillFormatter;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import ru.clevertec.tasks.olga.util.orm.impl.CartWorker;
import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@AllArgsConstructor
public class CartRepositoryImpl extends AbstractRepository implements CartRepository {

    private AbstractBillFormatter formatter;

    public CartRepositoryImpl(){
        this.formatter = new PseudographicBillFormatter();
    }


    @Override
    public void save(Cart cart, String path) {
        NodeWorker<Cart> worker = workerFactory.getCartWorker();
        Node cartNode = worker.modelToNode(cart);
        String fileName = path + ResourceBundle.getBundle("db").getString("path.bill_log");
        try {
            boolean isAppend = !isEmpty(fileName);
            if (isAppend){
                cartNode = cartNode.getChildNodes().get(0);
            }
            nodeTreeBuilder.writeXML(fileName, cartNode, isAppend);
        } catch (ServiceException e) {
            throw new WritingException(MessageLocaleService.getMessage("error.writing"));
        } catch (IOException e) {
            throw new ReadingException("error while reading");
        }
    }


    @Override
    public Cart findById(long id, String filePath){
        List<Cart> carts = getAll(filePath);
        for (Cart cart : carts){
            if (cart.getId() == id){
                return cart;
            }
        }
        throw new CartNotFoundException(MessageLocaleService.getMessage("error.bill_not_found"));
    }

    @Override
    public List<Cart> getAll(String path){
        Node node;
        CartWorker worker = (CartWorker) workerFactory.getCartWorker();
        List<Cart> log = new ArrayListImpl<>();
        String fileName = path + ResourceBundle.getBundle("db").getString("path.bill_log");
        try {
            node = nodeTreeBuilder.parseXML(fileName);
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
    public void printBill(Cart cart, String fileURI) {
        File file = new File(fileURI);
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

    private boolean isEmpty(String filePath) throws IOException {
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()){
                br.close();
                return false;
            }
        }
        br.close();
        return true;
    }
}
