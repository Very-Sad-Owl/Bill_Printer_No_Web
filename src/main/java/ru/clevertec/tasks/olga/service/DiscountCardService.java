package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;
import ru.clevertec.tasks.olga.exception.service.ServiceException;

public interface DiscountCardService extends CRUDService<DiscountCard, CardParamsDTO> {
    DiscountCard formCard(CardParamsDTO dto) throws ServiceException;
}
