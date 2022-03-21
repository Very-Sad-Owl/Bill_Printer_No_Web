package ru.clevertec.tasks.olga;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.printer.AbstractPrinter;
import ru.clevertec.tasks.olga.printer.impl.ConsolePrinter;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.argsparser.CartArgumentsSorter;
import ru.clevertec.tasks.olga.util.formatter.AbstractBillFormatter;
import ru.clevertec.tasks.olga.util.formatter.PseudographicBillFormatter;

@Slf4j
public class App {

    public static void main(String[] args) {
        AbstractBillFormatter billFormatter = new PseudographicBillFormatter();
        CartArgumentsSorter sorter = new CartArgumentsSorter();
        ServiceFactory factory = ServiceFactory.getInstance();
        AbstractPrinter consolePrinter = new ConsolePrinter();
        CartService cartService = factory.getCartService();

//        try {
//            log.info(MessageLocaleService.getMessage("label.guide"));
//            CartParamsDTO params = sorter.retrieveArgs(args);
//            switch (params.getAction()) {
//                case ACTION_PRINT:
//                    CartDto cartDto = CartDto.builder().cashierId(params.getCashier_id())
//                            .discountCardId(params.getCard_id())
//                            .goods(params.getGoods())
//                            .build();
//                    Cart cart = cartService.formCart(cartDto);
//                    long id = factory.getCartService().save(cart);
//                    cart.setId(id);
//                    List<String> bill = billFormatter.format(cart);
//                    consolePrinter.print(bill);
//                    break;
//                case ACTION_LOG:
//                    List<Cart> log = factory.getCartService().getAll();
//                    List<String> logStr = billFormatter.formatAll(log);
//                    consolePrinter.print(logStr);
//                    break;
//                case ACTION_FIND_BY_ID:
//                    Cart found = factory.getCartService().findById(params.getBill_id());
//                    List<String> billStr = billFormatter.format(found);
//                    consolePrinter.print(billStr);
//                    break;
//            }
//        } catch (LocalizedException e) {
//            log.error(e.getLocalizedMessage());
//        }

    }
}
//ResourceBundle.getBundle("db").getString("path.bill_log")
//1-7 2-3 card_uid-1 cashier_uid-2 action-print path-D:\botanstvo\java\billPinterNoWeb\db\
//action-log path-D:\botanstvo\java\billPinterNoWeb\db\
//action-find id-1 path-D:\botanstvo\java\billPinterNoWeb\db\