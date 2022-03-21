package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.controller.util.messages_provider.MessageProvider;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class CartManager implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProvider msgProvider = new MessageProvider(new Locale((String) request.getSession().getAttribute(LOCALE)));

        ServiceFactory provider = ServiceFactory.getInstance();
        CartService service = provider.getCartService();

//        long orderUid = Long.parseLong(request.getParameter(ORDER_UID));
//        String[] selectedPositions = request.getParameterValues(SELECTED_INGREDIENTS);
//        String[] selectedAmounts = request.getParameterValues(INGREDIENT_AMOUNT);
//        long selectedSpot = Long.parseLong(request.getParameter(AVAILABLE_SPOTS));
//        String selectedPaymet = request.getParameter(AVAILABLE_PAYMENT_METHODS);
//        int estimatedTime = Integer.parseInt(request.getParameter(ESTIMATED_TIME));
        try {
//            service.placeOrder(orderUid, selectedSpot, PaymentMethod.valueOf(selectedPaymet.toUpperCase()),
//                    selectedPositions, selectedAmounts, estimatedTime);
            response.getWriter().write("cart");
        } catch (Exception e) {
            response.getWriter().print(msgProvider.getMessage(e.getClass().getSimpleName()));
            log.error(e.getMessage());
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
