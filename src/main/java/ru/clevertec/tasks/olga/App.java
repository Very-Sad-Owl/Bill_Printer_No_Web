package ru.clevertec.tasks.olga;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.exception.LocalizedException;
import ru.clevertec.tasks.olga.model.*;
import ru.clevertec.tasks.olga.printer.AbstractPrinter;
import ru.clevertec.tasks.olga.printer.impl.ConsolePrinter;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.formatter.AbstractBillFormatter;
import ru.clevertec.tasks.olga.util.formatter.PseudographicBillFormatter;
import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.List;

import static ru.clevertec.tasks.olga.util.Constant.*;

@Slf4j
public class App
{

    public static void main( String[] args )
    {
        AbstractBillFormatter billFormatter = new PseudographicBillFormatter();
        ArgumentsSorter sorter = new ArgumentsSorter();
        ServiceFactory factory = ServiceFactory.getInstance();
        AbstractPrinter consolePrinter = new ConsolePrinter();

        try {
            log.info(MessageLocaleService.getMessage("label.guide"));
            ParamsDTO params = sorter.retrieveArgs(args);
            switch (params.getAction()) {
                case ACTION_PRINT:
                    Cashier cashier = factory.getCashierService()
                            .findById(params.getCashier_id(), params.getDataPath());
                    DiscountCard discountCard = factory.getDiscountCardService()
                            .findById(params.getCard_id(), params.getDataPath());
                    List<Slot> slots = factory.getCartService()
                            .formSlots(params.getGoods(), params.getDataPath());
                    Cart cart = factory.getCartService().formCart(slots, discountCard, cashier);
                    factory.getCartService().save(cart, params.getDataPath());
                    List<String> bill = billFormatter.format(cart);
                    consolePrinter.print(bill);
                    break;
                case ACTION_LOG:
                    List<Cart> log = factory.getCartService().getAll(params.getDataPath());
                    List<String> logStr = billFormatter.formatAll(log);
                    consolePrinter.print(logStr);
                    break;
                case ACTION_FIND_BY_ID:
                    Cart found = factory.getCartService().findById(params.getBill_id(), params.getDataPath());
                    List<String> billStr = billFormatter.format(found);
                    consolePrinter.print(billStr);
                    break;
            }
        } catch (LocalizedException e){
            log.info(e.getMessage());
        }
    }
}
//ResourceBundle.getBundle("db").getString("path.bill_log")
//1-7 2-3 card_uid-1 cashier_uid-2 action-print path-D:\botanstvo\java\billPinterNoWeb\db\
//action-log path-D:\botanstvo\java\billPinterNoWeb\db\
//action-find id-1 path-D:\botanstvo\java\billPinterNoWeb\db\