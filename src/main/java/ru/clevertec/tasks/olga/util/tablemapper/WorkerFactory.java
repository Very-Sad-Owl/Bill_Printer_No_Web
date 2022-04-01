package ru.clevertec.tasks.olga.util.tablemapper;

import ru.clevertec.tasks.olga.entity.*;
import ru.clevertec.tasks.olga.util.tablemapper.impl.*;
import lombok.Getter;

@Getter
public class WorkerFactory {

    private static final WorkerFactory instance = new WorkerFactory();

    private final NodeWorker<Product> productWorker = new ProductWorker();
    private final NodeWorker<DiscountCard> discountWorker = new DiscountCardWorker();
    private final NodeWorker<CardType> discountTypeWorker = new CardTypeWorker();
    private final NodeWorker<ProductDiscountType> productDiscountWorker = new ProductDiscountWorker();
    private final NodeWorker<Cashier> cashierWorker = new CashierWorker();
    private final NodeWorker<Cart> cartWorker = new CartWorker();
    private final NodeWorker<Slot> slotWorker = new SlotWorker();


    private WorkerFactory(){}


    public static WorkerFactory getInstance() {
        return instance;
    }
}
