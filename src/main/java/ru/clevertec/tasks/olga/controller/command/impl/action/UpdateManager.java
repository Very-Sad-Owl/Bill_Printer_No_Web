package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.controller.util.messages_provider.MessageProvider;
import ru.clevertec.tasks.olga.exception.GeneralException;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.argsparser.factory.SorterFactory;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.CommandParam.*;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class UpdateManager implements Command {

    public static final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    public static final SorterFactory sorterFactory = SorterFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProvider msgProvider = new MessageProvider(new Locale((String) request.getSession().getAttribute(LOCALE)));

        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            switch (parameterMap.get(TABLE)[0]){
                case CART:
                    CartParamsDTO cartParams = sorterFactory.getCartSorter().retrieveArgs(parameterMap);
                    Cart updatedCart = serviceFactory.getCartService().update(cartParams);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(updatedCart));
                    break;
                case PRODUCT:
                    ProductParamsDto productParams = sorterFactory.getProductSorter().retrieveArgs(parameterMap);
                    Product updatedProduct = serviceFactory.getProductService().update(productParams);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(updatedProduct));
                    break;
                case CASHIER:
                    CashierParamsDTO cashierParams = sorterFactory.getCashierSorter().retrieveArgs(parameterMap);
                    Cashier updatedCashier = serviceFactory.getCashierService().update(cashierParams);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                   response.getWriter().write(JsonMapper.parseObject(updatedCashier));
                    break;
            }
        } catch (GeneralException e) {
            response.getWriter().print(msgProvider.getMessage(e.getClass().getSimpleName()));
            log.error(e.getMessage());
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
