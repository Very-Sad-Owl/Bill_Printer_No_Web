package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.repository.models_repo.DiscountRepository;
import ru.clevertec.tasks.olga.service.models_service.DiscountCardService;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DiscountCardServiceImpl
        extends AbstractService<DiscountCard, DiscountRepository>
        implements DiscountCardService {

    private DiscountRepository discountRepository = repositoryFactory.getDiscountCardRepository();


    @Override
    public void save(DiscountCard discountCard) {

    }

    @Override
    public DiscountCard findById(long id) {
        return discountRepository.findById(id);
    }

    @Override
    public List<DiscountCard> getAll() {
        return discountRepository.getAll();
    }
}
