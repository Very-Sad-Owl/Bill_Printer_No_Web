package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.exception.UndefinedException;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;
import ru.clevertec.tasks.olga.controller.util.servlethelper.ResponseUtils;
import ru.clevertec.tasks.olga.dto.*;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.exception.GeneralException;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.argsparser.factory.SorterFactory;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.CommandParam.*;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class PrinterManager implements Command {

    public static final ServiceFactory factory = ServiceFactory.getInstance();
    public static final SorterFactory sorterFactory = SorterFactory.getInstance();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Locale locale = new Locale((String)request.getSession().getAttribute(LOCALE));
        MessageProvider msgProvider = new MessageProvider(locale);
        ServletOutputStream writer = response.getOutputStream();
        try {
            Path savedBillPath;
            Map<String, String[]> parameterMap = request.getParameterMap();
            RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(parameterMap);
            switch (requestParams.category){
                case CART:
                    CartParamsDTO cartParams = sorterFactory.getCartSorter().retrieveArgsFromMap(parameterMap, requestParams);
                    Cart cart = factory.getCartService().findById(cartParams.id);
                    savedBillPath = factory.getCartService().printBill(cart, locale);
                    byte[]content = Files.readAllBytes(savedBillPath);

                    ResponseUtils.setPdfType(response, savedBillPath.getFileName());
                    response.setContentLength(content.length);

                    writer.write(content);
                    break;
                case PRODUCT:
                    ProductParamsDto productParams = sorterFactory.getProductSorter()
                            .retrieveArgsFromMap(parameterMap, requestParams);
                    Product product = factory.getProductService().findById(productParams.id);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(product).getBytes(StandardCharsets.UTF_8));
                    break;
                case CASHIER:
                    CashierParamsDTO cashierParams = sorterFactory.getCashierSorter()
                            .retrieveArgsFromMap(parameterMap, requestParams);
                    Cashier cashier = factory.getCashierService().findById(cashierParams.id);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(cashier).getBytes(StandardCharsets.UTF_8));
                    break;
                case CARD:
                    CardParamsDTO cardParams = sorterFactory.getCardSorter()
                            .retrieveArgsFromMap(parameterMap, requestParams);
                    DiscountCard discountCard = factory.getDiscountCardService().findById(cardParams.id);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(discountCard).getBytes(StandardCharsets.UTF_8));
                    break;
                case CARD_TYPE:
                    CardTypeDto cardTypeParams = sorterFactory.getCardTypeSorter()
                            .retrieveArgsFromMap(parameterMap, requestParams);
                    CardType cardType = factory.getCardTypeService().findById(cardTypeParams.id);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(cardType).getBytes(StandardCharsets.UTF_8));
                    break;
                case PRODUCT_DISCOUNT:
                    ProductDiscountDTO discountParams = sorterFactory.getProductDiscountSorter()
                            .retrieveArgsFromMap(parameterMap, requestParams);
                    ProductDiscountType discountType = factory.getProductDiscount().findById(discountParams.id);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(discountType).getBytes(StandardCharsets.UTF_8));
                    break;
            }
        } catch (GeneralException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(msgProvider.getMessage(e.getClass().getSimpleName()));
            log.error(e.getClass().getSimpleName());
        } catch (Exception e) {
            log.error(UndefinedException.class.getSimpleName());
        }
    }
}
