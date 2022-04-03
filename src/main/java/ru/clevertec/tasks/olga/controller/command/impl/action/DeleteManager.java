package ru.clevertec.tasks.olga.controller.command.impl.action;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.exception.UndefinedExceptionCustom;
import ru.clevertec.tasks.olga.service.*;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;
import ru.clevertec.tasks.olga.controller.util.servlethelper.RequestUtils;
import ru.clevertec.tasks.olga.controller.util.servlethelper.ResponseUtils;
import ru.clevertec.tasks.olga.dto.*;
import ru.clevertec.tasks.olga.exception.CustomGeneralException;
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
@Component
public class DeleteManager implements Command {

    private final Gson gson = GsonFactory.getInstance().getGson();
    private CartService cartService;
    private DiscountCardService cardService;
    private CashierService cashierService;
    private ProductService productService;
    private ProductDiscountService productDiscountService;
    private CardTypeService cardTypeService;
    private MessageSource messageSource;

    @Autowired
    public DeleteManager(CartService cartService, DiscountCardService cardService, CashierService cashierService,
                         ProductService productService, ProductDiscountService productDiscountService,
                         CardTypeService cardTypeService, MessageSource messageSource) {
        this.cartService = cartService;
        this.cardService = cardService;
        this.cashierService = cashierService;
        this.productService = productService;
        this.productDiscountService = productDiscountService;
        this.cardTypeService = cardTypeService;
        this.messageSource = messageSource;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        PrintWriter writer = response.getWriter();
        BufferedReader reader = request.getReader();
        Locale locale = new Locale((String) request.getSession().getAttribute(LOCALE));
        try {
            String requestBody = RequestUtils.readBody(reader);
            RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(parameterMap);
            boolean isDeleted = false;
            switch (requestParams.category) {
                case CART:
                    CartParamsDTO cartParams = gson.fromJson(requestBody, CartParamsDTO.class);
                    isDeleted = cartService.delete(cartParams.id);
                    break;
                case PRODUCT:
                    ProductParamsDto productParams = gson.fromJson(requestBody, ProductParamsDto.class);
                    isDeleted = productService.delete(productParams.id);
                    break;
                case CASHIER:
                    CashierParamsDTO cashierParams = gson.fromJson(requestBody, CashierParamsDTO.class);
                    isDeleted = cashierService.delete(cashierParams.id);
                    break;
                case CARD:
                    CardParamsDTO cardParams = gson.fromJson(requestBody, CardParamsDTO.class);
                    isDeleted = cardService.delete(cardParams.id);
                    break;
                case CARD_TYPE:
                    CardTypeDto cardTypeParams = gson.fromJson(requestBody, CardTypeDto.class);
                    isDeleted = cardTypeService.delete(cardTypeParams.id);
                    break;
                case PRODUCT_DISCOUNT:
                    ProductDiscountDTO discountParams = gson.fromJson(requestBody, ProductDiscountDTO.class);
                    isDeleted = productDiscountService.delete(discountParams.id);
                    break;
            }
            ResponseUtils.setJsonType(response);
            if (isDeleted) {
                writer.write(JsonMapper.parseObject(
                                messageSource.getMessage("label.delete_operation_success", null, locale)
                        )
                );
            } else {
                writer.write(JsonMapper.parseObject(
                                messageSource.getMessage("label.delete_operation_fail", null, locale)
                        )
                );
            }
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
