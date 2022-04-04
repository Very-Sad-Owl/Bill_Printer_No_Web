package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.service.*;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;
import java.util.List;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.CommandParam.*;

@Slf4j
@Component
public class LogManager implements Command {

    CartService cartService;
    DiscountCardService cardService;
    CashierService cashierService;
    ProductService productService;
    ProductDiscountService productDiscountService;
    CardTypeService cardTypeService;

    @Autowired
    public LogManager(CartService cartService, DiscountCardService cardService, CashierService cashierService,
                      ProductService productService, ProductDiscountService productDiscountService, CardTypeService cardTypeService) {
        this.cartService = cartService;
        this.cardService = cardService;
        this.cashierService = cashierService;
        this.productService = productService;
        this.productDiscountService = productDiscountService;
        this.cardTypeService = cardTypeService;
    }

    @Override
    public Object execute(String requestBody, Map<String, String[]> params) {
        RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(params);
        switch (requestParams.category) {
            case CART:
                List<Cart> carts = cartService.getAll(requestParams.nodesPerPage, requestParams.offset);
                return JsonMapper.parseObject(carts);
            case PRODUCT:
                List<Product> products = productService
                        .getAll(requestParams.nodesPerPage, requestParams.offset);
                return JsonMapper.parseObject(products);
            case CASHIER:
                List<Cashier> cashiers = cashierService
                        .getAll(requestParams.nodesPerPage, requestParams.offset);
                return JsonMapper.parseObject(cashiers);
            case CARD:
                List<DiscountCard> discountCard = cardService
                        .getAll(requestParams.nodesPerPage, requestParams.offset);
                return JsonMapper.parseObject(discountCard);
            case CARD_TYPE:
                List<CardType> cardType = cardTypeService
                        .getAll(requestParams.nodesPerPage, requestParams.offset);
                return JsonMapper.parseObject(cardType);
            case PRODUCT_DISCOUNT:
                List<ProductDiscountType> discountType = productDiscountService
                        .getAll(requestParams.nodesPerPage, requestParams.offset);
                return JsonMapper.parseObject(discountType);
            default:
                return "null";
        }
    }
}
