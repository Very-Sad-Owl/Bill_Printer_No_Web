package ru.clevertec.tasks.olga.util.orm;

import ru.clevertec.tasks.olga.model.*;
import ru.clevertec.tasks.olga.util.orm.impl.*;
import lombok.Getter;

@Getter
public class WorkerFactory {

    private static final WorkerFactory instance = new WorkerFactory();

    private final NodeWorker<Product> productWorker = new ProductWorker();
    private final NodeWorker<DiscountCard> discountWorker = new DiscountCardWorker();
    private final NodeWorker<ProductDiscountType> productDiscountWorker = new ProductDiscountWorker();
    private final NodeWorker<Cashier> cashierWorker = new CashierWorker();
    private final NodeWorker<Cart> cartWorker = new CartWorker();
    private final NodeWorker<Slot> slotWorker = new SlotWorker();


    private WorkerFactory(){}


    public static WorkerFactory getInstance() {
        return instance;
    }
}
