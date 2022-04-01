package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.dto.CardTypeDto;

public interface CardTypeService extends CRUDService<CardType, CardTypeDto> {
    CardType formDiscount(CardTypeDto dto);
}