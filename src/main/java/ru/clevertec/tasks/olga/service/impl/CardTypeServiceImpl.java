package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.exception.CardNotFoundExceptionCustom;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.repository.CardTypeRepository;
import ru.clevertec.tasks.olga.service.CardTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class CardTypeServiceImpl
        extends AbstractService
        implements CardTypeService {

    private final CardTypeRepository discountRepo;

    @Autowired
    public CardTypeServiceImpl(CardTypeRepository discountRepo) {
        this.discountRepo = discountRepo;
    }

    @Override
    public CardType save(CardTypeDto dto) {
        CardType type = formDiscount(dto);
        long insertedId = discountRepo.save(type);
        type.setId(insertedId);
        return type;
    }

    @Override
    public CardType findById(long id) {
        Optional<CardType> discount = discountRepo.findById(id);
        if (discount.isPresent()){
            return discount.get();
        } else {
            throw new CardNotFoundExceptionCustom("error.card_not_found");
        }
    }

    @Override
    public List<CardType> getAll(int limit, int offset) {
        return discountRepo.getAll(limit, offset);
    }

    @Override
    public boolean delete(long id) {
        return discountRepo.delete(id);
    }

    @Override
    public CardType update(CardTypeDto dto) {
        CardType toUpdate = findById(dto.id);
        toUpdate = CardType.builder()
                .id(dto.id)
                .title(!dto.title.equals(Defaults.defaultValue(String.class)) ? dto.title : toUpdate.getTitle())
                .discount(dto.discountVal != Defaults.defaultValue(Double.TYPE) ? dto.discountVal : toUpdate.getDiscount())
                .build();
        discountRepo.update(toUpdate);
        return toUpdate;
    }

    @Override
    public CardType formDiscount(CardTypeDto dto) {
        return CardType.builder()
                .id(dto.id)
                .title(dto.title)
                .discount(dto.discountVal)
                .build();
    }
}
