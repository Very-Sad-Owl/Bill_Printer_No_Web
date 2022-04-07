package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.exception.handeled.NotFoundExceptionHandled;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.repository.CardTypeRepository;
import ru.clevertec.tasks.olga.service.CardTypeService;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.util.validation.CRUDParamsValidator.*;

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
    @SneakyThrows
    public CardType save(CardTypeDto dto) {
        validateDtoForSave(dto);
        CardType type = formDiscount(dto);
        long insertedId = discountRepo.save(type);
        type.setId(insertedId);
        return type;
    }

    @Override
    @SneakyThrows
    public CardType findById(long id) {
        validateId(id);
        Optional<CardType> discount = discountRepo.findById(id);
        if (discount.isPresent()) {
            return discount.get();
        } else {
            throw new NotFoundExceptionHandled();
        }
    }

    @Override
    @SneakyThrows
    public List<CardType> getAll(int limit, int offset) {
        return discountRepo.getAll(limit, offset);
    }

    @Override
    @SneakyThrows
    public void delete(long id) {
        validateId(id);
        discountRepo.delete(id);
    }

    @Override
    @SneakyThrows
    public CardType update(CardTypeDto dto) {
        validatePartlyFilledObject(dto);
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
