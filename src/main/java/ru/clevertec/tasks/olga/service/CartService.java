package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.dto.CartParamsDTO;
import ru.clevertec.tasks.olga.exception.service.ServiceException;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface CartService extends CRUDService<Cart, CartParamsDTO> {

    List<Slot> formSlots(Map<Long, Integer> goods) throws ServiceException;
    Slot formSlot(Map.Entry<Long, Integer> pair) throws ServiceException;
    Cart formCart(CartParamsDTO cartParamsDTO) throws ServiceException;
    Path printBill(Cart cart, Locale locale);

}
