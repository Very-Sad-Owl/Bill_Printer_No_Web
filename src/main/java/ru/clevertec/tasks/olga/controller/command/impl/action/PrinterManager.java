package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.controller.command.resource.CommandUrlPath;
import ru.clevertec.tasks.olga.controller.util.messages_provider.MessageProvider;
import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.MessageLocaleService;
import ru.clevertec.tasks.olga.util.argsparser.CartArgumentsSorter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class PrinterManager implements Command {

    public static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final CartService cartService = ServiceFactory.getInstance().getCartService();
    private static final CartArgumentsSorter CART_ARGUMENTS_SORTER =
            new CartArgumentsSorter();
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProvider msgProvider = new MessageProvider
                (new Locale((String) request.getSession().getAttribute(LOCALE)));
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            switch (parameterMap.get("table")[0]){
                case "cart":
                    response.getWriter().write(cartService.getAll().toString());
                    break;
                case "product":
                    response.getWriter().write(factory.getProductService().getAll().toString());
                    break;
            }
            String mapAsString = parameterMap.keySet().stream()
                    .map(key -> key + "=" + Arrays.asList(parameterMap.get(key)))
                    .collect(Collectors.joining(", ", "{", "}"));
            log.info(mapAsString);
            CartParamsDTO cartParamsDTO = CART_ARGUMENTS_SORTER.retrieveArgs(parameterMap);
            Cart cart = cartService.formCart(cartParamsDTO);
//            cartService.save(cart);
            cartService.printBill(cart);
            log.info(cart.toString());
            response.getWriter().write(MessageLocaleService.getMessage("label.guide",
                    new Locale((String) request.getSession().getAttribute(LOCALE))) +
                    cart.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            response.getWriter().print(msgProvider.getMessage(e.getClass().getSimpleName()));
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(CommandUrlPath.CART_PAGE);
        requestDispatcher.forward(request, response);

    }

}
