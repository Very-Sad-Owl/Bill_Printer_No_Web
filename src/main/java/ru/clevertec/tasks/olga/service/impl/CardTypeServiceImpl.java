package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.exception.crud.notfound.BillNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.CardNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.CardTypeNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.ProductNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.repository.RepositoryException;
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
    public CardType save(CardTypeDto dto) {
        validateFullyFilledDto(dto);
        try {
            CardType type = formDiscount(dto);
            long insertedId = discountRepo.save(type);
            type.setId(insertedId);
            return type;
        } catch (RepositoryException e) {
            throw new SavingExceptionHandled(e);
        }
    }

    @Override
    @SneakyThrows
    public CardType findById(long id) {
        validateId(id);
        try {
            Optional<CardType> discount = discountRepo.findById(id);
            if (discount.isPresent()) {
                return discount.get();
            } else {
                throw new CardTypeNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new CardNotFoundExceptionHandled(e);
        }
    }

    @Override
    @SneakyThrows
    public List<CardType> getAll(int limit, int offset) {
        return discountRepo.getAll(limit, offset);
    }

    @Override
    public void delete(long id) {
        validateId(id);
        try {
            if (!discountRepo.delete(id)) {
                throw new CardTypeNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e);
        }
    }

    @Override
    public CardType patch(CardTypeDto dto) {
        validatePartlyFilledObject(dto);
        CardType toUpdate;
        try {
            toUpdate = findById(dto.id);
            toUpdate = CardType.builder()
                    .id(dto.id)
                    .title(!dto.title.equals(Defaults.defaultValue(String.class)) ? dto.title : toUpdate.getTitle())
                    .discount(dto.discountVal != Defaults.defaultValue(Double.TYPE) ? dto.discountVal : toUpdate.getDiscount())
                    .build();
            discountRepo.update(toUpdate);
            return toUpdate;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    public CardType put(CardTypeDto dto) {
        validateFullyFilledDto(dto);
        try {
            CardType updated = formDiscount(dto);
            if (!discountRepo.update(updated)) {
                throw new UpdatingExceptionHandled(new CardTypeNotFoundExceptionHandled(dto.id + ""));
            }
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
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
