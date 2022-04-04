package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.controller.util.servlethelper.RequestUtils;
import ru.clevertec.tasks.olga.exception.UndefinedExceptionCustom;
import ru.clevertec.tasks.olga.service.*;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;
import ru.clevertec.tasks.olga.controller.util.servlethelper.ResponseUtils;
import ru.clevertec.tasks.olga.dto.*;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.exception.CustomGeneralException;
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
@Component
public class PrinterManager implements Command {

    public static final SorterFactory sorterFactory = SorterFactory.getInstance();
    CartService cartService;
    DiscountCardService cardService;
    CashierService cashierService;
    ProductService productService;
    ProductDiscountService productDiscountService;
    CardTypeService cardTypeService;

    @Autowired
    public PrinterManager(CartService cartService, DiscountCardService cardService, CashierService cashierService,
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
        try {
            requestBody = RequestUtils.readBody(requestBody);
            RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(params);
            Path savedBillPath;
            switch (requestParams.category) {
                case CART:
                    CartParamsDTO cartParams = sorterFactory.getCartSorter()
                            .retrieveArgsFromMap(params, requestParams);
                    Cart cart = cartService.findById(cartParams.id);
                    savedBillPath = cartService.printBill(cart, new Locale(requestParams.locale));
                    return Files.readAllBytes(savedBillPath);
                case PRODUCT:
                    ProductParamsDto productParams = sorterFactory.getProductSorter()
                            .retrieveArgsFromMap(params, requestParams);
                    Product product = productService.findById(productParams.id);
                    return JsonMapper.parseObject(product).getBytes(StandardCharsets.UTF_8);
                case CASHIER:
                    CashierParamsDTO cashierParams = sorterFactory.getCashierSorter()
                            .retrieveArgsFromMap(params, requestParams);
                    Cashier cashier = cashierService.findById(cashierParams.id);
                    return JsonMapper.parseObject(cashier).getBytes(StandardCharsets.UTF_8);
                case CARD:
                    CardParamsDTO cardParams = sorterFactory.getCardSorter()
                            .retrieveArgsFromMap(params, requestParams);
                    DiscountCard discountCard = cardService.findById(cardParams.id);
                    return JsonMapper.parseObject(discountCard).getBytes(StandardCharsets.UTF_8);
                case CARD_TYPE:
                    CardTypeDto cardTypeParams = sorterFactory.getCardTypeSorter()
                            .retrieveArgsFromMap(params, requestParams);
                    CardType cardType = cardTypeService.findById(cardTypeParams.id);
                    return JsonMapper.parseObject(cardType).getBytes(StandardCharsets.UTF_8);
                case PRODUCT_DISCOUNT:
                    ProductDiscountDTO discountParams = sorterFactory.getProductDiscountSorter()
                            .retrieveArgsFromMap(params, requestParams);
                    ProductDiscountType discountType = productDiscountService.findById(discountParams.id);
                    return JsonMapper.parseObject(discountType).getBytes(StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "null";
    }
}
