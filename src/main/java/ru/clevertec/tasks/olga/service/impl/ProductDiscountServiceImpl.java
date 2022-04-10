package ru.clevertec.tasks.olga.service.impl;

import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.dto.ProductDiscountDTO;
import ru.clevertec.tasks.olga.exception.crud.notfound.BillNotFoundException;
import ru.clevertec.tasks.olga.exception.crud.notfound.NotFoundException;
import ru.clevertec.tasks.olga.exception.crud.notfound.ProductDiscountNotFoundException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
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
        } catch (EmptyResultDataAccessException | NotFoundException e) {
            throw new SavingException(e);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public ProductDiscountType findById(long id) {
        validateId(id);
        try {
            Optional<ProductDiscountType> discount = discountRepo.findById(id);
            if (discount.isPresent()) {
                return discount.get();
            } else {
                throw new ProductDiscountNotFoundException(id + "");
            }
        } catch (EmptyResultDataAccessException | NotFoundException e) {
            throw new ProductDiscountNotFoundException(id + "");
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<ProductDiscountType> getAll(Pageable pageable) {
        try {
        return discountRepo.getAll(pageable);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        validateId(id);
        try {
            if (!discountRepo.delete(id)) {
                throw new DeletionException(new BillNotFoundException(id + ""));
            }
        } catch (EmptyResultDataAccessException e) {
            throw new DeletionException(new ProductDiscountNotFoundException(id + ""));
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
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
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        }
        try {
            discountRepo.update(updated);
            return updated;
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public ProductDiscountType put(ProductDiscountDTO dto) {
        validateFullyFilledDto(dto);
        try {
            ProductDiscountType updated = formDiscount(dto);
            if (!discountRepo.update(updated)) {
                throw new UpdatingException(new ProductDiscountNotFoundException(dto.id + ""));
            }
            return updated;
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
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

