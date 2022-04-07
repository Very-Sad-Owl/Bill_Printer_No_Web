package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;
import ru.clevertec.tasks.olga.exception.crud.notfound.BillNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.CardNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.ProductNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.repository.RepositoryException;
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
        } catch (RepositoryException | NotFoundExceptionHandled e) {
            throw new SavingExceptionHandled(e);
        }
    }

    @Override
    @SneakyThrows
    public DiscountCard findById(long id) {
        validateId(id);
        try {
            Optional<DiscountCard> card = cardRepo.findById(id);
            if (card.isPresent()) {
                return card.get();
            } else {
                throw new CardNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
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
                throw new BillNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e);
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
        } catch (NotFoundExceptionHandled e) {
            throw new UpdatingExceptionHandled(e);
        }
        try {
            cardRepo.update(updated);
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    public DiscountCard put(CardParamsDTO dto) {
        validateFullyFilledDto(dto);
        try {
            DiscountCard updated = formCard(dto);
            if (!cardRepo.update(updated)) {
                throw new UpdatingExceptionHandled(new CardNotFoundExceptionHandled(dto.id + ""));
            }
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    public DiscountCard formCard(CardParamsDTO dto) {
        try {
            return DiscountCard.builder()
                    .id(dto.id)
                    .birthday(LocalDate.parse(dto.birthday))
                    .cardType(discountService.findById(dto.discountId))
                    .build();
        } catch (NotFoundExceptionHandled e) {
            throw new NotFoundExceptionHandled(e);
        }
    }
}
