package ru.clevertec.tasks.olga.repository.factory;

import ru.clevertec.tasks.olga.repository.*;
import ru.clevertec.tasks.olga.repository.impl.*;
import lombok.Getter;

@Getter
public class RepositoryFactory {

    private RepositoryFactory(){}

    private static final RepositoryFactory instance = new RepositoryFactory();

    private final CartRepository cartRepo = new CartRepositoryImpl();
    private final CashierRepository cashierRepository = new CashierRepositoryImpl();
    private final DiscountCardRepository discountCardRepository = new DiscountCardRepositoryImpl();
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final SlotRepository slotRepository = new SlotRepositoryImpl();
    private final ProductDiscountRepository productDiscountRepository = new ProductDiscountRepositoryImpl();

    public static RepositoryFactory getInstance(){
        return instance;
    }

}
