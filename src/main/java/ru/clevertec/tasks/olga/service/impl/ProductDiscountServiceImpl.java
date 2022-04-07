package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.dto.ProductDiscountDTO;
import ru.clevertec.tasks.olga.exception.crud.notfound.BillNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.ProductDiscountNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.ProductNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.repository.RepositoryException;
import ru.clevertec.tasks.olga.repository.ProductDiscountRepository;
import ru.clevertec.tasks.olga.service.ProductDiscountService;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.util.validation.CRUDParamsValidator.*;

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
    public ProductDiscountType save(ProductDiscountDTO dto) {
        validateFullyFilledDto(dto);
        try {
            ProductDiscountType type = formDiscount(dto);
            long insertedId = discountRepo.save(type);
            type.setId(insertedId);
            return type;
        } catch (RepositoryException | NotFoundExceptionHandled e) {
            throw new SavingExceptionHandled(e);
        }
    }

    @Override
    @SneakyThrows
    public ProductDiscountType findById(long id) {
        validateId(id);
        try {
            Optional<ProductDiscountType> discount = discountRepo.findById(id);
            if (discount.isPresent()) {
                return discount.get();
            } else {
                throw new ProductDiscountNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<ProductDiscountType> getAll(int limit, int offset) {
        return discountRepo.getAll(limit, offset);
    }

    @Override
    public void delete(long id) {
        validateId(id);
        try {
            if (!discountRepo.delete(id)) {
                throw new BillNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e);
        }
    }

    @Override
    public ProductDiscountType patch(ProductDiscountDTO params) {
        validatePartlyFilledObject(params);
        ProductDiscountType updated;
        try {
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
            updated = formDiscount(newProduct);
        } catch (NotFoundExceptionHandled e) {
            throw new UpdatingExceptionHandled(e);
        }
        try {
            discountRepo.update(updated);
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    public ProductDiscountType put(ProductDiscountDTO dto) {
        validateFullyFilledDto(dto);
        try {
            ProductDiscountType updated = formDiscount(dto);
            if (!discountRepo.update(updated)) {
                throw new UpdatingExceptionHandled(new ProductDiscountNotFoundExceptionHandled(dto.id + ""));
            }
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    public ProductDiscountType formDiscount(ProductDiscountDTO dto) {
        try {
            return ProductDiscountType.builder()
                    .id(dto.id)
                    .title(dto.title)
                    .discount(dto.val)
                    .requiredMinQuantity(dto.requiredQuantity)
                    .build();
        } catch (NotFoundExceptionHandled e) {
            throw new NotFoundExceptionHandled(e);
        }
    }
}

