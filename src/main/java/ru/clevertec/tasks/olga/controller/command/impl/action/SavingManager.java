package ru.clevertec.tasks.olga.controller.command.impl.action;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.exception.UndefinedException;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;
import ru.clevertec.tasks.olga.controller.util.servlethelper.RequestUtils;
import ru.clevertec.tasks.olga.controller.util.servlethelper.ResponseUtils;
import ru.clevertec.tasks.olga.dto.*;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.exception.GeneralException;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.jsonmapper.GsonFactory;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

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
public class SavingManager implements Command {

    public static final ServiceFactory factory = ServiceFactory.getInstance();
    private final Gson gson = GsonFactory.getInstance().getGson();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MessageProvider msgProvider = new MessageProvider(new Locale((String) request.getSession().getAttribute(LOCALE)));
        Map<String, String[]> parameterMap = request.getParameterMap();
        PrintWriter writer = response.getWriter();
        BufferedReader reader = request.getReader();
        try {
            RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(parameterMap);
            String requestBody = RequestUtils.readBody(reader);
            switch (requestParams.category){
                case CART:
                    CartParamsDTO cartParams =  gson.fromJson(requestBody, CartParamsDTO.class);
                    Cart cart = factory.getCartService().save(cartParams);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(cart));
                    break;
                case PRODUCT:
                    ProductParamsDto productParams = gson.fromJson(requestBody, ProductParamsDto.class);
                    Product product = factory.getProductService().save(productParams);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(product));
                    break;
                case CASHIER:
                    CashierParamsDTO cashierParams = gson.fromJson(requestBody, CashierParamsDTO.class);
                    Cashier cashier = factory.getCashierService().save(cashierParams);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(cashier));
                    break;
                case CARD_TYPE:
                    CardTypeDto cardTypeParams = gson.fromJson(requestBody, CardTypeDto.class);
                    CardType cardType = factory.getCardTypeService().save(cardTypeParams);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(cardType));
                    break;
                case CARD:
                    CardParamsDTO cardParams = gson.fromJson(requestBody, CardParamsDTO.class);
                    DiscountCard discountCard = factory.getDiscountCardService().save(cardParams);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(discountCard));
                    break;
                case PRODUCT_DISCOUNT:
                    ProductDiscountDTO discountParams = gson.fromJson(requestBody, ProductDiscountDTO.class);
                    ProductDiscountType discountType = factory.getProductDiscount().save(discountParams);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(discountType));
                    break;
            }
        } catch (GeneralException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(msgProvider.getMessage(e.getClass().getSimpleName()));
            log.error(e.getClass().getSimpleName());
        } catch (Exception e){
            log.error(UndefinedException.class.getSimpleName());
        } finally {
            reader.close();
            writer.flush();
            writer.close();
        }
    }
}
