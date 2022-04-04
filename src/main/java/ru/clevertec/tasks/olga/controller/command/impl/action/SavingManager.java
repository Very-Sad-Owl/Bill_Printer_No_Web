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
public class SavingManager implements Command {

    private Gson gson;
    CartService cartService;
    DiscountCardService cardService;
    CashierService cashierService;
    ProductService productService;
    ProductDiscountService productDiscountService;
    CardTypeService cardTypeService;

    @Autowired
    public SavingManager(Gson gson, CartService cartService, DiscountCardService cardService, CashierService cashierService,
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
    public Object execute(String requestBody, Map<String, String[]> params) {
        requestBody = RequestUtils.readBody(requestBody);
        RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(params);
        switch (requestParams.category) {
            case CART:
                CartParamsDTO cartParams = gson.fromJson(requestBody, CartParamsDTO.class);
                Cart cart = cartService.save(cartParams);
                return JsonMapper.parseObject(cart);
            case PRODUCT:
                ProductParamsDto productParams = gson.fromJson(requestBody, ProductParamsDto.class);
                Product product = productService.save(productParams);
                return JsonMapper.parseObject(product);
            case CASHIER:
                CashierParamsDTO cashierParams = gson.fromJson(requestBody, CashierParamsDTO.class);
                Cashier cashier = cashierService.save(cashierParams);
                return JsonMapper.parseObject(cashier);
            case CARD_TYPE:
                CardTypeDto cardTypeParams = gson.fromJson(requestBody, CardTypeDto.class);
                CardType cardType = cardTypeService.save(cardTypeParams);
                return JsonMapper.parseObject(cardType);
            case CARD:
                CardParamsDTO cardParams = gson.fromJson(requestBody, CardParamsDTO.class);
                DiscountCard discountCard = cardService.save(cardParams);
                return JsonMapper.parseObject(discountCard);
            case PRODUCT_DISCOUNT:
                ProductDiscountDTO discountParams = gson.fromJson(requestBody, ProductDiscountDTO.class);
                ProductDiscountType discountType = productDiscountService.save(discountParams);
                return JsonMapper.parseObject(discountType);
            default:
                log.error("default");
        }
        return "null";
    }
}
