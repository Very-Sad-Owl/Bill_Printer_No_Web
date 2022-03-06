package ru.clevertec.tasks.olga.util.node_converter;

import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.util.node_converter.impl.CartWorker;
import ru.clevertec.tasks.olga.util.node_converter.impl.CashierWorker;
import ru.clevertec.tasks.olga.util.node_converter.impl.DiscountCardWorker;
import ru.clevertec.tasks.olga.util.node_converter.impl.ProductWorker;
import lombok.Getter;

@Getter
public class WorkerFactory {

    private static final WorkerFactory instance = new WorkerFactory();

    private final NodeWorker<Product> productWorker = new ProductWorker();
    private final NodeWorker<DiscountCard> discountWorker = new DiscountCardWorker();
    private final NodeWorker<Cashier> cashierWorker = new CashierWorker();
    private final NodeWorker<Cart> cartWorker = new CartWorker();


    private WorkerFactory(){}


    public static WorkerFactory getInstance() {
        return instance;
    }
}
