package ru.clevertec.tasks.olga.controller.command.impl.action;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.exception.UndefinedExceptionCustom;
import ru.clevertec.tasks.olga.service.*;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;
import ru.clevertec.tasks.olga.controller.util.servlethelper.RequestUtils;
import ru.clevertec.tasks.olga.controller.util.servlethelper.ResponseUtils;
import ru.clevertec.tasks.olga.dto.*;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.exception.CustomGeneralException;
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
@Component
public class UpdateManager implements Command {

    private final Gson gson = GsonFactory.getInstance().getGson();
    CartService cartService;
    DiscountCardService cardService;
    CashierService cashierService;
    ProductService productService;
    ProductDiscountService productDiscountService;
    CardTypeService cardTypeService;

    @Autowired
    public UpdateManager(CartService cartService, DiscountCardService cardService, CashierService cashierService,
                         ProductService productService, ProductDiscountService productDiscountService, CardTypeService cardTypeService) {
        this.cartService = cartService;
        this.cardService = cardService;
        this.cashierService = cashierService;
        this.productService = productService;
        this.productDiscountService = productDiscountService;
        this.cardTypeService = cardTypeService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        PrintWriter writer = response.getWriter();
        ResponseUtils.setJsonType(response);
        try(BufferedReader reader = request.getReader() ) {
            String requestBody = RequestUtils.readBody(reader);
            RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(parameterMap);
            switch (requestParams.category){
                case CART:
                    CartParamsDTO cartParams = gson.fromJson(requestBody, CartParamsDTO.class);
                    Cart updatedCart = cartService.update(cartParams);
                    writer.write(JsonMapper.parseObject(updatedCart));
                    break;
                case PRODUCT:
                    ProductParamsDto productParams = gson.fromJson(requestBody, ProductParamsDto.class);
                    Product updatedProduct = productService.update(productParams);
                    writer.write(JsonMapper.parseObject(updatedProduct));
                    break;
                case CASHIER:
                    CashierParamsDTO cashierParams = gson.fromJson(requestBody, CashierParamsDTO.class);
                    Cashier updatedCashier = cashierService.update(cashierParams);
                    writer.write(JsonMapper.parseObject(updatedCashier));
                    break;
                case CARD:
                    CardParamsDTO cardParams = gson.fromJson(requestBody, CardParamsDTO.class);
                    DiscountCard discountCard = cardService.update(cardParams);
                    writer.write(JsonMapper.parseObject(discountCard));
                    break;
                case CARD_TYPE:
                    CardTypeDto cardTypeParams = gson.fromJson(requestBody, CardTypeDto.class);
                    CardType cardType = cardTypeService.update(cardTypeParams);
                    writer.write(JsonMapper.parseObject(cardType));
                    break;
                case PRODUCT_DISCOUNT:
                    ProductDiscountDTO discountParams = gson.fromJson(requestBody, ProductDiscountDTO.class);
                    ProductDiscountType discountType = productDiscountService.update(discountParams);
                    writer.write(JsonMapper.parseObject(discountType));
                    break;
            }
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
