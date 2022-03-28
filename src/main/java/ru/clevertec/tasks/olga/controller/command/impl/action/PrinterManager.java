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
import ru.clevertec.tasks.olga.printer.impl.PdfPrinter;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.argsparser.factory.SorterFactory;
import ru.clevertec.tasks.olga.util.formatter.AbstractBillFormatter;
import ru.clevertec.tasks.olga.util.formatter.PseudographicBillFormatter;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.CommandParam.*;
import static ru.clevertec.tasks.olga.controller.command.resource.RequestParam.CATEGORY;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class PrinterManager implements Command {

    public static final ServiceFactory factory = ServiceFactory.getInstance();
    public static final SorterFactory sorterFactory = SorterFactory.getInstance();
        private static final PdfPrinter pdfPrinter = new PdfPrinter();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Locale locale = new Locale((String)request.getSession().getAttribute(LOCALE));
        MessageProvider msgProvider = new MessageProvider(locale);
        try {
            Path res;
            Map<String, String[]> parameterMap = request.getParameterMap();
            switch (parameterMap.get(CATEGORY)[0]){
                case CART:
                    AbstractBillFormatter formatter = new PseudographicBillFormatter(locale);
                    CartParamsDTO cartParams = sorterFactory.getCartSorter().retrieveArgs(parameterMap);
                    Cart cart = factory.getCartService().findById(cartParams.id);
                    res = pdfPrinter.printAsFile(formatter.format(cart));
                    byte[]content = Files.readAllBytes(res);

                    response.setContentType("application/pdf");
                    response.addHeader("Content-Disposition", "attachment; filename=" + res.getFileName());
                    response.setContentLength(content.length);
                    response.getOutputStream().write(content);
                    break;
                case PRODUCT:
                    ProductParamsDto productParams = sorterFactory.getProductSorter().retrieveArgs(parameterMap);
                    Product product = factory.getProductService().findById(productParams.id);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(product));
                    break;
                case CASHIER:
                    CashierParamsDTO cashierParams = sorterFactory.getCashierSorter().retrieveArgs(parameterMap);
                    Cashier cashier = factory.getCashierService().findById(cashierParams.id);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonMapper.parseObject(cashier));
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

    }

}
