package ru.clevertec.tasks.olga.controller.command.impl.action;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.service.*;
import ru.clevertec.tasks.olga.controller.util.servlethelper.RequestUtils;
import ru.clevertec.tasks.olga.dto.*;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;
import java.util.Map;
import static ru.clevertec.tasks.olga.controller.command.resource.CommandParam.*;

@Slf4j
@Component
public class UpdateManager implements Command {

    private Gson gson;
    CartService cartService;
    DiscountCardService cardService;
    CashierService cashierService;
    ProductService productService;
    ProductDiscountService productDiscountService;
    CardTypeService cardTypeService;

    @Autowired
    public UpdateManager(Gson gson, CartService cartService, DiscountCardService cardService, CashierService cashierService,
                         ProductService productService, ProductDiscountService productDiscountService, CardTypeService cardTypeService) {
        this.gson = gson;
        this.cartService = cartService;
        this.cardService = cardService;
        this.cashierService = cashierService;
        this.productService = productService;
        this.productDiscountService = productDiscountService;
        this.cardTypeService = cardTypeService;
    }

    @Override
    public Object execute(String body, Map<String, String[]> params) {
        String requestBody = RequestUtils.readBody(body);
        RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(params);
        switch (requestParams.category) {
            case CART:
                CartParamsDTO cartParams = gson.fromJson(requestBody, CartParamsDTO.class);
                Cart updatedCart = cartService.update(cartParams);
                return JsonMapper.parseObject(updatedCart);
            case PRODUCT:
                ProductParamsDto productParams = gson.fromJson(requestBody, ProductParamsDto.class);
                Product updatedProduct = productService.update(productParams);
                return JsonMapper.parseObject(updatedProduct);
            case CASHIER:
                CashierParamsDTO cashierParams = gson.fromJson(requestBody, CashierParamsDTO.class);
                Cashier updatedCashier = cashierService.update(cashierParams);
                return JsonMapper.parseObject(updatedCashier);
            case CARD:
                CardParamsDTO cardParams = gson.fromJson(requestBody, CardParamsDTO.class);
                DiscountCard discountCard = cardService.update(cardParams);
                return JsonMapper.parseObject(discountCard);
            case CARD_TYPE:
                CardTypeDto cardTypeParams = gson.fromJson(requestBody, CardTypeDto.class);
                CardType cardType = cardTypeService.update(cardTypeParams);
                return JsonMapper.parseObject(cardType);
            case PRODUCT_DISCOUNT:
                ProductDiscountDTO discountParams = gson.fromJson(requestBody, ProductDiscountDTO.class);
                ProductDiscountType discountType = productDiscountService.update(discountParams);
                return JsonMapper.parseObject(discountType);
        }
        return "null";
    }
}
