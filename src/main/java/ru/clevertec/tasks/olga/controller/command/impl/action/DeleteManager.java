package ru.clevertec.tasks.olga.controller.command.impl.action;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;
import ru.clevertec.tasks.olga.controller.util.servlethelper.RequestUtils;
import ru.clevertec.tasks.olga.controller.util.servlethelper.ResponseUtils;
import ru.clevertec.tasks.olga.dto.*;
import ru.clevertec.tasks.olga.exception.GeneralException;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.localization.MessageLocaleService;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.jsonmapper.GsonFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.CommandParam.*;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class DeleteManager implements Command {

    public static final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final Gson gson = GsonFactory.getInstance().getGson();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MessageProvider msgProvider = new MessageProvider(new Locale((String) request.getSession().getAttribute(LOCALE)));
        Map<String, String[]> parameterMap = request.getParameterMap();
        PrintWriter writer = response.getWriter();
        try (BufferedReader reader = request.getReader()) {
            String requestBody = RequestUtils.readBody(reader);
            RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(parameterMap);

            switch (requestParams.category){
                case CART:
                    CartParamsDTO cartParams = gson.fromJson(requestBody, CartParamsDTO.class);
                    serviceFactory.getCartService().delete(cartParams.id);
                    ResponseUtils.setPlainTextType(response);
                    writer.write(MessageLocaleService.getMessage("label.delete_operation_success"));
                    break;
                case PRODUCT:
                    ProductParamsDto productParams = gson.fromJson(requestBody, ProductParamsDto.class);
                    serviceFactory.getProductService().delete(productParams.id);
                    ResponseUtils.setPlainTextType(response);
                    writer.write(MessageLocaleService.getMessage("label.delete_operation_success"));
                    break;
                case CASHIER:
                    CashierParamsDTO cashierParams = gson.fromJson(requestBody, CashierParamsDTO.class);
                    serviceFactory.getCashierService().delete(cashierParams.id);
                    ResponseUtils.setPlainTextType(response);
                    writer.write(MessageLocaleService.getMessage("label.delete_operation_success"));
                    break;
                case CARD:
                    CardParamsDTO cardParams = gson.fromJson(requestBody, CardParamsDTO.class);
                    serviceFactory.getDiscountCardService()
                            .delete(cardParams.id);
                    ResponseUtils.setJsonType(response);
                    writer.write(MessageLocaleService.getMessage("label.delete_operation_success"));
                    break;
                case CARD_TYPE:
                    CardTypeDto cardTypeParams = gson.fromJson(requestBody, CardTypeDto.class);
                    serviceFactory.getCardTypeService()
                            .delete(cardTypeParams.id);
                    ResponseUtils.setJsonType(response);
                    writer.write(MessageLocaleService.getMessage("label.delete_operation_success"));
                    break;
                case PRODUCT_DISCOUNT:
                    ProductDiscountDTO discountParams = gson.fromJson(requestBody, ProductDiscountDTO.class);
                    serviceFactory.getProductDiscount()
                            .delete(discountParams.id);
                    ResponseUtils.setJsonType(response);
                    writer.write(MessageLocaleService.getMessage("label.delete_operation_success"));
                    break;
            }
        } catch (GeneralException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(msgProvider.getMessage(e.getClass().getSimpleName()));
            log.error(e.getMessage());
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
