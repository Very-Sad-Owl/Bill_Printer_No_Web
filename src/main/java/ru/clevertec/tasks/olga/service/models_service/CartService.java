package ru.clevertec.tasks.olga.service.models_service;

import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.Slot;
import ru.clevertec.tasks.olga.service.CRUDService;

import java.util.List;
import java.util.Map;

public interface CartService extends CRUDService<Cart> {

    List<Slot> formSlots(Map<Long, Integer> goods);
    Cart formCart(List<Slot> goods, DiscountCard card, Cashier cashier);
    void printBill(Cart cart, String destinationPath);

}
