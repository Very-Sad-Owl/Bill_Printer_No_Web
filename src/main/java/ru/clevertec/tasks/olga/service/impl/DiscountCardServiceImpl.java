package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.exception.CardNotFoundException;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.dto.CardParamsDTO;
import ru.clevertec.tasks.olga.repository.DiscountCardRepository;
import ru.clevertec.tasks.olga.service.CardTypeService;
import ru.clevertec.tasks.olga.service.DiscountCardService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DiscountCardServiceImpl
        extends AbstractService<DiscountCard, CardParamsDTO, DiscountCardRepository>
        implements DiscountCardService {

    private static final DiscountCardRepository cardRepo = repoFactory.getDiscountCardRepository();
    private static final CardTypeService discountService = new CardTypeImpl();

    @Override
    public DiscountCard save(CardParamsDTO dto) {
        DiscountCard card = formCard(dto);
        long insertedId =  cardRepo.save(card);
        card.setId(insertedId);
        return card;
    }

    @Override
    public DiscountCard findById(long id) {
        Optional<DiscountCard> card = cardRepo.findById(id);
        if (card.isPresent()){
            return card.get();
        } else {
            throw new CardNotFoundException("error.card_not_found");
        }
    }

    @Override
    public List<DiscountCard> getAll(int limit, int offset) {
        return cardRepo.getAll(limit, offset);
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public DiscountCard update(CardParamsDTO dto) {
        return null;
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
