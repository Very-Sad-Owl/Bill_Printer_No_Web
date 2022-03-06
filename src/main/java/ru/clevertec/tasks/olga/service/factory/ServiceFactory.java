package ru.clevertec.tasks.olga.service.factory;

import ru.clevertec.tasks.olga.service.impl.CartServiceImpl;
import ru.clevertec.tasks.olga.service.impl.CashierServiceImpl;
import ru.clevertec.tasks.olga.service.impl.DiscountCardServiceImpl;
import ru.clevertec.tasks.olga.service.impl.ProductServiceImpl;
import ru.clevertec.tasks.olga.service.models_service.CartService;
import ru.clevertec.tasks.olga.service.models_service.CashierService;
import ru.clevertec.tasks.olga.service.models_service.DiscountCardService;
import ru.clevertec.tasks.olga.service.models_service.ProductService;
import lombok.Getter;

@Getter
public class ServiceFactory {

    private ServiceFactory(){}

    private static final ServiceFactory instance = new ServiceFactory();

    private final CartService cartService = new CartServiceImpl();
    private final CashierService cashierService = new CashierServiceImpl();
    private final DiscountCardService discountCardService = new DiscountCardServiceImpl();
    private final ProductService productService = new ProductServiceImpl();

    public static ServiceFactory getInstance(){
        return instance;
    }
}
