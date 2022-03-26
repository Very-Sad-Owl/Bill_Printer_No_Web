package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.exception.CardNotFoundException;
import ru.clevertec.tasks.olga.model.DiscountCard;
import ru.clevertec.tasks.olga.model.ProductDiscountType;
import ru.clevertec.tasks.olga.repository.ProductDiscountRepository;
import ru.clevertec.tasks.olga.service.DiscountCardService;
import ru.clevertec.tasks.olga.service.ProductDiscountService;

import java.util.List;
import java.util.Optional;

public class ProductDiscountImpl
        extends AbstractService<ProductDiscountType, ProductDiscountRepository>
        implements ProductDiscountService {

    private static final ProductDiscountRepository discountRepo = repoFactory.getProductDiscountRepository();

    @Override
    public long save(ProductDiscountType productDiscountType) {
        return discountRepo.save(productDiscountType);
    }

    @Override
    public ProductDiscountType findById(long id) {
        Optional<ProductDiscountType> discount = discountRepo.findById(id);
        if (discount.isPresent()){
            return discount.get();
        } else {
            throw new CardNotFoundException("error.card_not_found");
        }
    }

    @Override
    public List<ProductDiscountType> getAll(int limit, int offset) {
        return discountRepo.getAll(limit, offset);
    }

    @Override
    public boolean delete(long id) {
        return discountRepo.delete(id);
    }

    @Override
    public ProductDiscountType update(long id, ProductDiscountType productDiscountType) {
        return null;
    }
}
