package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.controller.util.messages_provider.MessageProvider;
import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.model.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.model.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.argsparser.factory.SorterFactory;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class SavingManager implements Command {

    public static final ServiceFactory factory = ServiceFactory.getInstance();
    public static final SorterFactory sorterFactory = SorterFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProvider msgProvider = new MessageProvider(new Locale((String) request.getSession().getAttribute(LOCALE)));

        ServiceFactory provider = ServiceFactory.getInstance();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            long insertedId;
            switch (parameterMap.get("table")[0]){
                case "cart":
                    CartParamsDTO cartParams = sorterFactory.getCartSorter().retrieveArgs(parameterMap);
                    Cart cart = factory.getCartService().formCart(cartParams);
                    insertedId = factory.getCartService()
                            .save(cart);
                    cart.setId(insertedId);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(cart));
                    break;
                case "product":
                    ProductParamsDto productParams = sorterFactory.getProductSorter().retrieveArgs(parameterMap);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
//                    response.getWriter().write(JsonMapper.parseObject(products));
                    break;
                case "cashier":
                    CashierParamsDTO cashierParams = sorterFactory.getCashierSorter().retrieveArgs(parameterMap);
                    Cashier cashier = factory.getCashierService().formCashier(cashierParams);
                    insertedId = factory.getCashierService().save(cashier);
                    cashier.setId(insertedId);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                   response.getWriter().write(JsonMapper.parseObject(cashier));
                    break;
            }
        } catch (Exception e) {
            response.getWriter().print(msgProvider.getMessage(e.getClass().getSimpleName()));
            log.error(e.getMessage());
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
