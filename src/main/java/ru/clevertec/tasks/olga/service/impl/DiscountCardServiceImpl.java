package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.repository.DiscountRepository;
import ru.clevertec.tasks.olga.service.DiscountCardService;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DiscountCardServiceImpl
        extends AbstractService<DiscountCard, DiscountRepository>
        implements DiscountCardService {

    private DiscountRepository discountRepository = repositoryFactory.getDiscountCardRepository();


    @Override
    public void save(DiscountCard discountCard, String fileName) {

    }

    @Override
    public DiscountCard findById(long id, String filePath) {
        return discountRepository.findById(id, filePath);
    }

    @Override
    public List<DiscountCard> getAll(String filePath) {
        return discountRepository.getAll(filePath);
    }
}
