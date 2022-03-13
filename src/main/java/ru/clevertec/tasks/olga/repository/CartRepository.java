package ru.clevertec.tasks.olga.repository;

import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.repository.CRUDRepository;
import ru.clevertec.tasks.olga.util.formatter.AbstractBillFormatter;

public interface CartRepository extends CRUDRepository<Cart> {

    void setFormatter(AbstractBillFormatter formatter);
    void printBill(Cart cart, String path);

}
