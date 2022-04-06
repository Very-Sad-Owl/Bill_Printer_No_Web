package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.exception.repoexc.RepositoryException;
import ru.clevertec.tasks.olga.exception.serviceexc.DeletionExceptionHandled;
import ru.clevertec.tasks.olga.exception.serviceexc.NotFoundExceptionHandled;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;
import ru.clevertec.tasks.olga.exception.serviceexc.SavingExceptionHandled;
import ru.clevertec.tasks.olga.exception.serviceexc.ServiceException;
import ru.clevertec.tasks.olga.repository.DiscountCardRepository;
import ru.clevertec.tasks.olga.service.CardTypeService;
import ru.clevertec.tasks.olga.service.DiscountCardService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    @SneakyThrows
    public DiscountCard save(CardParamsDTO dto) {
            DiscountCard card = formCard(dto);
            long insertedId = cardRepo.save(card);
            card.setId(insertedId);
            return card;
    }

    @Override
    @SneakyThrows
    public DiscountCard findById(long id) {
            Optional<DiscountCard> card = cardRepo.findById(id);
            if (card.isPresent()) {
                return card.get();
            } else {
                throw new NotFoundExceptionHandled("error.card_not_found");
            }
    }

    @Override
    @SneakyThrows
    public List<DiscountCard> getAll(int limit, int offset) {
            return cardRepo.getAll(limit, offset);
    }

    @Override
    @SneakyThrows
    public void delete(long id) {
            cardRepo.delete(id);
    }

    @Override
    @SneakyThrows
    public DiscountCard update(CardParamsDTO params) {
        DiscountCard original = findById(params.id);
        CardParamsDTO newProduct = CardParamsDTO.builder()
                .id(params.id)
                .birthday(params.birthday == null
                        ? original.getBirthday()+""
                        : params.birthday)
                .discountId(params.discountId == Defaults.defaultValue(Long.TYPE)
                        ? original.getCardType().getId()
                        : params.discountId)
                .build();
        DiscountCard updated = formCard(newProduct);
            cardRepo.update(updated);
            return updated;
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
