package ru.clevertec.tasks.olga;

import ru.clevertec.tasks.olga.exception.LocalizedException;
import ru.clevertec.tasks.olga.model.*;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.args_parser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.formatter.AbstractBillFormatter;
import ru.clevertec.tasks.olga.util.formatter.PseudographicBillFormatter;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.List;

import static ru.clevertec.tasks.olga.util.Constant.*;
import static ru.clevertec.tasks.olga.util.MessageLocaleService.getMessage;

public class App
{

    public static void main( String[] args )
    {
        AbstractBillFormatter billFormatter = new PseudographicBillFormatter();
        ArgumentsSorter sorter = new ArgumentsSorter();
        ServiceFactory factory = ServiceFactory.getInstance();

        try {
            System.out.println(MessageLocaleService.getMessage("label.guide"));
            ParamsDTO params = sorter.retrieveArgs(args);
            switch (params.getAction()) {
                case ACTION_PRINT:
                    Cashier cashier = factory.getCashierService().findById(params.getCashier_id());
                    DiscountCard discountCard = factory.getDiscountCardService().findById(params.getCard_id());
                    List<Slot> slots = factory.getCartService().formSlots(params.getGoods());
                    Cart cart = factory.getCartService().formCart(slots, discountCard, cashier);
                    List<String> bill = billFormatter.format(cart);
                    for (String line : bill){
                        System.out.println(line);
                    }
                    break;
                case ACTION_LOG:
                    List<Cart> log = factory.getCartService().getAll();
                    List<String> logStr = billFormatter.formatAll(log);
                    for (String line : logStr){
                        System.out.println(line);
                    }
                    break;
                case ACTION_FIND_BY_ID:
                    Cart found = factory.getCartService().findById(params.getBill_id());
                    List<String> billStr = billFormatter.format(found);
                    for (String line : billStr){
                        System.out.println(line);
                    }
                    break;
            }
        } catch (LocalizedException e){
            System.out.println(e.getMessage());
        }
    }
}
