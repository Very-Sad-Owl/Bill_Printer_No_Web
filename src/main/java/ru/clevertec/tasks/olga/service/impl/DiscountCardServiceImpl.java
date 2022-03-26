package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.exception.CardNotFoundException;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.repository.DiscountCardRepository;
import ru.clevertec.tasks.olga.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.tasks.olga.service.DiscountCardService;

import java.util.List;
import java.util.Optional;

public class DiscountCardServiceImpl implements DiscountCardService {

    private static final DiscountCardRepository cardRepo = new DiscountCardRepositoryImpl();

    @Override
    public long save(DiscountCard discountCard) {
        return cardRepo.save(discountCard);
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
    public boolean delete(DiscountCard discountCard, String filePath) {
        return false;
    }

    @Override
    public DiscountCard update(DiscountCard discountCard, String filePath) {
        return null;
    }
}
