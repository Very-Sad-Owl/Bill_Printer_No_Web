package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.exception.repoexc.RepositoryException;
import ru.clevertec.tasks.olga.exception.serviceexc.DeletionExceptionHandled;
import ru.clevertec.tasks.olga.exception.serviceexc.NotFoundExceptionHandled;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.dto.ProductDiscountDTO;
import ru.clevertec.tasks.olga.exception.serviceexc.SavingExceptionHandled;
import ru.clevertec.tasks.olga.exception.serviceexc.ServiceException;
import ru.clevertec.tasks.olga.repository.ProductDiscountRepository;
import ru.clevertec.tasks.olga.service.ProductDiscountService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDiscountServiceImpl
        extends AbstractService
        implements ProductDiscountService {

    private final ProductDiscountRepository discountRepo;

    @Autowired
    public ProductDiscountServiceImpl(ProductDiscountRepository discountRepo) {
        this.discountRepo = discountRepo;
    }

    @Override
    @SneakyThrows
    public ProductDiscountType save(ProductDiscountDTO dto) {
        ProductDiscountType type = formDiscount(dto);
        long insertedId = discountRepo.save(type);
        type.setId(insertedId);
        return type;
    }

    @Override
    @SneakyThrows
    public ProductDiscountType findById(long id) {
        Optional<ProductDiscountType> discount = discountRepo.findById(id);
        if (discount.isPresent()) {
            return discount.get();
        } else {
            throw new NotFoundExceptionHandled("error.card_not_found");
        }
    }

    @Override
    @SneakyThrows
    public List<ProductDiscountType> getAll(int limit, int offset) {
        return discountRepo.getAll(limit, offset);
    }

    @Override
    public void delete(long id) {
        discountRepo.delete(id);
    }

    @Override
    @SneakyThrows
    public ProductDiscountType update(ProductDiscountDTO params) {
        ProductDiscountType original = findById(params.id);
        ProductDiscountDTO newProduct = ProductDiscountDTO.builder()
                .id(params.id)
                .title(params.title == null
                        ? original.getTitle()
                        : params.title)
                .requiredQuantity(params.requiredQuantity == Defaults.defaultValue(Integer.TYPE)
                        ? original.getRequiredMinQuantity()
                        : params.requiredQuantity)
                .build();
        ProductDiscountType updated = formDiscount(newProduct);
        discountRepo.update(updated);
        return updated;
    }

    @Override
    public ProductDiscountType formDiscount(ProductDiscountDTO dto) {
        return ProductDiscountType.builder()
                .id(dto.id)
                .title(dto.title)
                .discount(dto.val)
                .requiredMinQuantity(dto.requiredQuantity)
                .build();
    }
}

