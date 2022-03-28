package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.controller.command.resource.CommandUrlPath;
import ru.clevertec.tasks.olga.controller.util.messages_provider.MessageProvider;
import ru.clevertec.tasks.olga.exception.GeneralException;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.printer.impl.PdfPrinter;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.argsparser.factory.SorterFactory;
import ru.clevertec.tasks.olga.util.formatter.AbstractBillFormatter;
import ru.clevertec.tasks.olga.util.formatter.PseudographicBillFormatter;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.CommandParam.*;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class LogManager implements Command {

    public static final ServiceFactory factory = ServiceFactory.getInstance();
    public static final SorterFactory sorterFactory = SorterFactory.getInstance();
    private static final CartService cartService = ServiceFactory.getInstance().getCartService();
    private static final PdfPrinter pdfPrinter = new PdfPrinter();
    private static final AbstractBillFormatter formatter = new PseudographicBillFormatter();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageProvider msgProvider = new MessageProvider
                (new Locale((String) request.getSession().getAttribute(LOCALE)));
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            switch (parameterMap.get("table")[0]){
                case CART:
                    CartParamsDTO cartParams = sorterFactory.getCartSorter().retrieveArgs(parameterMap);
                    List<Cart> carts = cartService.getAll(cartParams.nodesPerPage, cartParams.offset);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(carts));
                    break;
                case PRODUCT:
                    ProductParamsDto productParams = sorterFactory.getProductSorter().retrieveArgs(parameterMap);
                    List<Product> products = factory.getProductService()
                            .getAll(productParams.nodesPerPage, productParams.offset);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(products));
                    break;
                case CASHIER:
                    CashierParamsDTO cashierParams = sorterFactory.getCashierSorter().retrieveArgs(parameterMap);
                    List<Cashier> cashiers = factory.getCashierService()
                            .getAll(cashierParams.nodesPerPage, cashierParams.offset);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(cashiers));
                    break;
            }
        } catch (GeneralException e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(msgProvider.getMessage(e.getClass().getSimpleName()));
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(CommandUrlPath.CART_PAGE);
        requestDispatcher.forward(request, response);

    }

}
