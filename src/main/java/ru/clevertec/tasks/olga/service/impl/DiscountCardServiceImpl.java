package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;
import ru.clevertec.tasks.olga.exception.crud.notfound.BillNotFoundException;
import ru.clevertec.tasks.olga.exception.crud.notfound.CardNotFoundException;
import ru.clevertec.tasks.olga.exception.crud.notfound.NotFoundException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.DiscountCardRepository;
import ru.clevertec.tasks.olga.service.CardTypeService;
import ru.clevertec.tasks.olga.service.DiscountCardService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.util.validation.CRUDParamsValidator.*;

@Service
public class DiscountCardServiceImpl
        extends AbstractService
        implements DiscountCardService {

    private final DiscountCardRepository cardRepo;
    private final CardTypeService discountService;

    @Autowired
    public DiscountCardServiceImpl(DiscountCardRepository cardRepo, CardTypeService discountService) {
        this.cardRepo = cardRepo;
        this.discountService = discountService;
    }

    @Override
    public DiscountCard save(CardParamsDTO dto) {
        validateFullyFilledDto(dto);
        try {
            DiscountCard card = formCard(dto);
            long insertedId = cardRepo.save(card);
            card.setId(insertedId);
            return card;
        } catch (RepositoryException | NotFoundException e) {
            throw new SavingException(e);
        }
    }

    @Override
    public DiscountCard findById(long id) {
        validateId(id);
        try {
            Optional<DiscountCard> card = cardRepo.findById(id);
            if (card.isPresent()) {
                return card.get();
            } else {
                throw new CardNotFoundException(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<DiscountCard> getAll(int limit, int offset) {
        return cardRepo.getAll(limit, offset);
    }

    @Override
    public void delete(long id) {
        validateId(id);
        try {
            if (!cardRepo.delete(id)) {
                throw new DeletionException(new BillNotFoundException(id + ""));
            }
        } catch (RepositoryException e) {
            throw new UndefinedException(e);
        }
    }

    @Override
    public DiscountCard patch(CardParamsDTO params) {
        validatePartlyFilledObject(params);
        DiscountCard updated;
        try {
            DiscountCard original = findById(params.id);
            CardParamsDTO newProduct = CardParamsDTO.builder()
                    .id(params.id)
                    .birthday(params.birthday == null
                            ? original.getBirthday() + ""
                            : params.birthday)
                    .discountId(params.discountId == Defaults.defaultValue(Long.TYPE)
                            ? original.getCardType().getId()
                            : params.discountId)
                    .build();
            updated = formCard(newProduct);
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        }
        try {
            cardRepo.update(updated);
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public DiscountCard put(CardParamsDTO dto) {
        validateFullyFilledDto(dto);
        try {
            DiscountCard updated = formCard(dto);
            if (!cardRepo.update(updated)) {
                throw new UpdatingException(new CardNotFoundException(dto.id + ""));
            }
            return updated;
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        } catch (RepositoryException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public DiscountCard formCard(CardParamsDTO dto) {
        return DiscountCard.builder()
                .id(dto.id)
                .birthday(LocalDate.parse(dto.birthday))
                .cardType(discountService.findById(dto.discountId))
                .build();
    }
}
