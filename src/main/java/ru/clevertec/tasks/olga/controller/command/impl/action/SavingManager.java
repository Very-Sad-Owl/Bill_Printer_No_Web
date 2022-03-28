package ru.clevertec.tasks.olga.controller.command.impl.action;

import com.google.gson.Gson;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class SavingManager implements Command {

    public static final ServiceFactory factory = ServiceFactory.getInstance();
    public static final SorterFactory sorterFactory = SorterFactory.getInstance();
    private final Gson gson = new Gson();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProvider msgProvider = new MessageProvider(new Locale((String) request.getSession().getAttribute(LOCALE)));

        Map<String, String[]> parameterMap = request.getParameterMap();
        BufferedReader reader = request.getReader();
        String requestBody = reader.lines()
                .reduce("", String::concat);
        String gsonxd = gson.toJson(requestBody);
        List<String> args = Arrays.asList(gson.fromJson(requestBody, String[].class));

        try {
            switch (parameterMap.get("table")[0]){
                case "cart":
                    CartParamsDTO cartParams = sorterFactory.getCartSorter().retrieveArgs(parameterMap);
                    Cart cart = factory.getCartService().save(cartParams);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(cart));
                    break;
                case "product":
                    ProductParamsDto productParams = sorterFactory.getProductSorter().retrieveArgs(parameterMap);
                    Product product = factory.getProductService().save(productParams);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(product));
                    break;
                case "cashier":
                    CashierParamsDTO cashierParams = sorterFactory.getCashierSorter().retrieveArgs(parameterMap);
                    Cashier cashier = factory.getCashierService().save(cashierParams);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                   response.getWriter().write(JsonMapper.parseObject(cashier));
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
