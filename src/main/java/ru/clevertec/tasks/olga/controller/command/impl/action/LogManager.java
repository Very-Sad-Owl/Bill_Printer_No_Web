package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.exception.UndefinedExceptionCustom;
import ru.clevertec.tasks.olga.service.*;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;
import ru.clevertec.tasks.olga.controller.util.servlethelper.ResponseUtils;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.exception.CustomGeneralException;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(parameterMap);
            ResponseUtils.setJsonType(response);
            switch (requestParams.category){
                case CART:
                    List<Cart> carts = cartService.getAll(requestParams.nodesPerPage, requestParams.offset);
                    writer.write(JsonMapper.parseObject(carts));
                    break;
                case PRODUCT:
                    List<Product> products = productService
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    writer.write(JsonMapper.parseObject(products));
                    break;
                case CASHIER:
                    List<Cashier> cashiers = cashierService
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    writer.write(JsonMapper.parseObject(cashiers));
                    break;
                case CARD:
                    List<DiscountCard> discountCard = cardService
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    writer.write(JsonMapper.parseObject(discountCard));
                    break;
                case CARD_TYPE:
                    List<CardType> cardType = cardTypeService
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    writer.write(JsonMapper.parseObject(cardType));
                    break;
                case PRODUCT_DISCOUNT:
                    List<ProductDiscountType> discountType = productDiscountService
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    writer.write(JsonMapper.parseObject(discountType));
                    break;
            }
        } finally {
            writer.flush();
            writer.close();
        }
    }

}
