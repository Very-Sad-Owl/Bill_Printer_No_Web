package ru.clevertec.tasks.olga.service;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface CartService extends CRUDService<Cart, CartParamsDTO> {

    List<Slot> formSlots(Map<Long, Integer> goods);
    Slot formSlot(Map.Entry<Long, Integer> pair);
    Cart formCart(CartParamsDTO cartParamsDTO);
    Path printBill(Cart cart, Locale locale);

}
