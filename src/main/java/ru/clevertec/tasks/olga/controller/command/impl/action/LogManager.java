package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.controller.util.messagesprovider.MessageProvider;
import ru.clevertec.tasks.olga.controller.util.servlethelper.ResponseUtils;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.dto.ProductDiscountDTO;
import ru.clevertec.tasks.olga.dto.RequestParamsDto;
import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.exception.GeneralException;
import ru.clevertec.tasks.olga.service.CartService;
import ru.clevertec.tasks.olga.service.factory.ServiceFactory;
import ru.clevertec.tasks.olga.util.argsparser.ArgumentsSorter;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.CommandParam.*;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class LogManager implements Command {

    public static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final CartService cartService = ServiceFactory.getInstance().getCartService();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MessageProvider msgProvider = new MessageProvider
                (new Locale((String) request.getSession().getAttribute(LOCALE)));
        PrintWriter writer = response.getWriter();
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            RequestParamsDto requestParams = ArgumentsSorter.retrieveBaseArgs(parameterMap);
            switch (requestParams.category){
                case CART:
                    List<Cart> carts = cartService.getAll(requestParams.nodesPerPage, requestParams.offset);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(carts));
                    break;
                case PRODUCT:
                    List<Product> products = factory.getProductService()
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(products));
                    break;
                case CASHIER:
                    List<Cashier> cashiers = factory.getCashierService()
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(cashiers));
                    break;
                case CARD:
                    List<DiscountCard> discountCard = factory.getDiscountCardService()
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(discountCard));
                    break;
                case CARD_TYPE:
                    List<CardType> cardType = factory.getCardTypeService()
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(cardType));
                    break;
                case PRODUCT_DISCOUNT:
                    List<ProductDiscountType> discountType = factory.getProductDiscount()
                            .getAll(requestParams.nodesPerPage, requestParams.offset);
                    ResponseUtils.setJsonType(response);
                    writer.write(JsonMapper.parseObject(discountType));
                    break;
            }
        } catch (GeneralException e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(msgProvider.getMessage(e.getClass().getSimpleName()));
        }
    }

}
