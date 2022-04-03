package ru.clevertec.tasks.olga.service;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;

public interface DiscountCardService extends CRUDService<DiscountCard, CardParamsDTO> {
    DiscountCard formCard(CardParamsDTO dto);
}
