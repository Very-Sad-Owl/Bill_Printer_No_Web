package ru.clevertec.tasks.olga.service.factory;

import ru.clevertec.tasks.olga.service.*;
import ru.clevertec.tasks.olga.service.impl.*;
import lombok.Getter;

@Getter
public class ServiceFactory {

    private ServiceFactory(){}

    private static final ServiceFactory instance = new ServiceFactory();

    private final CartService cartService = new CartServiceImpl();
    private final CashierService cashierService = new CashierServiceImpl();
    private final DiscountCardService discountCardService = new DiscountCardServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final ProductDiscountService productDiscount = new ProductDiscountServiceImpl();
    private final CardTypeService cardTypeService = new CardTypeServiceImpl();

    public static ServiceFactory getInstance(){
        return instance;
    }
}
