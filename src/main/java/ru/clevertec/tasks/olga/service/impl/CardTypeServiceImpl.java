package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.dto.CardTypeDto;
import ru.clevertec.tasks.olga.exception.crud.notfound.CardNotFoundException;
import ru.clevertec.tasks.olga.exception.crud.notfound.CardTypeNotFoundException;
import ru.clevertec.tasks.olga.exception.crud.notfound.NotFoundException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
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
        } catch (EmptyResultDataAccessException | NotFoundException e) {
            throw new SavingException(e);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public CardType findById(long id) {
        validateId(id);
        try {
            Optional<CardType> discount = discountRepo.findById(id);
            if (discount.isPresent()) {
                return discount.get();
            } else {
                throw new CardTypeNotFoundException(id + "");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new CardTypeNotFoundException(id + "");
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<CardType> getAll(Pageable pageable) {
        try {
            return discountRepo.getAll(pageable);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        validateId(id);
        try {
            if (!discountRepo.delete(id)) {
                throw new DeletionException(new CardTypeNotFoundException(id + ""));
            }
        } catch (EmptyResultDataAccessException e) {
            throw new DeletionException(new NotFoundException(id + ""));
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
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
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public CardType put(CardTypeDto dto) {
        validateFullyFilledDto(dto);
        try {
            CardType updated = formDiscount(dto);
            if (!discountRepo.update(updated)) {
                throw new UpdatingException(new CardTypeNotFoundException(dto.id + ""));
            }
            return updated;
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
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
