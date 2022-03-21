package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.Slot;
import ru.clevertec.tasks.olga.model.dto.CartParamsDTO;

import java.util.List;
import java.util.Map;

public interface CartService extends CRUDService<Cart> {

    List<Slot> formSlots(Map<Long, Integer> goods);
    Cart formCart(CartParamsDTO cartParamsDTO);
    void printBill(Cart cart);

}
