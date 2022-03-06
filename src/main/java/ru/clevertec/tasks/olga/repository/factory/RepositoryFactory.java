package ru.clevertec.tasks.olga.repository.factory;

import ru.clevertec.tasks.olga.repository.impl.CartRepositoryImpl;
import ru.clevertec.tasks.olga.repository.impl.CashierRepositoryImpl;
import ru.clevertec.tasks.olga.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.tasks.olga.repository.impl.ProductRepositoryImpl;
import ru.clevertec.tasks.olga.repository.models_repo.CartRepository;
import ru.clevertec.tasks.olga.repository.models_repo.CashierRepository;
import ru.clevertec.tasks.olga.repository.models_repo.DiscountRepository;
import ru.clevertec.tasks.olga.repository.models_repo.ProductRepository;
import lombok.Getter;

@Getter
public class RepositoryFactory {

    private RepositoryFactory(){}

    private static final RepositoryFactory instance = new RepositoryFactory();

    private final CartRepository cartRepo = new CartRepositoryImpl();
    private final CashierRepository cashierRepository = new CashierRepositoryImpl();
    private final DiscountRepository discountCardRepository = new DiscountCardRepositoryImpl();
    private final ProductRepository productRepository = new ProductRepositoryImpl();

    public static RepositoryFactory getInstance(){
        return instance;
    }

}
