package ru.clevertec.tasks.olga.service.impl;


import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.exception.crud.notfound.BillNotFoundException;
import ru.clevertec.tasks.olga.exception.crud.notfound.NotFoundException;
import ru.clevertec.tasks.olga.exception.crud.notfound.ProductNotFoundException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.service.ProductDiscountService;
import ru.clevertec.tasks.olga.service.ProductService;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.util.validation.CRUDParamsValidator.*;

@Service
public class ProductServiceImpl
        extends AbstractService
        implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDiscountService discountService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductDiscountService discountService) {
        this.productRepository = productRepository;
        this.discountService = discountService;
    }

    @Override
    public Product save(ProductParamsDto dto) {
        validateFullyFilledDto(dto);
        try {
            Product product = formProduct(dto);
            long insertedId = productRepository.save(product);
            product.setId(insertedId);
            return product;
        } catch (EmptyResultDataAccessException | NotFoundException e) {
            throw new SavingException(e);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public Product findById(long id) {
        validateId(id);
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                return product.get();
            } else {
                throw new ProductNotFoundException(id + "");
            }
        } catch (EmptyResultDataAccessException | NotFoundException e) {
            throw new ProductNotFoundException(id + "");
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<Product> getAll(Pageable pageable) {
        try {
        return productRepository.getAll(pageable);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        validateId(id);
        try {
            if (!productRepository.delete(id)) {
                throw new DeletionException(new BillNotFoundException(id + ""));
            }
        } catch (EmptyResultDataAccessException e) {
            throw new DeletionException(new ProductNotFoundException(id + ""));
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public Product patch(ProductParamsDto params) {
        validatePartlyFilledObject(params);
        Product updated;
        try {
            Product original = findById(params.id);
            ProductParamsDto newProduct = ProductParamsDto.builder()
                    .id(params.id)
                    .title(params.title == null
                            ? original.getTitle()
                            : params.title)
                    .price(params.price == Defaults.defaultValue(Double.TYPE)
                            ? original.getPrice()
                            : params.price)
                    .discount_id(params.discount_id == Defaults.defaultValue(Long.TYPE)
                            ? original.getDiscountType().getId()
                            : params.discount_id)
                    .build();
            updated = formProduct(newProduct);
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        }
        try {
            productRepository.update(updated);
            return updated;
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public Product put(ProductParamsDto dto) {
        validateFullyFilledDto(dto);
        try {
            Product updated = formProduct(dto);
            if (!productRepository.update(updated)) {
                throw new UpdatingException(new ProductNotFoundException(dto.id + ""));
            }
            return updated;
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        } catch (DataAccessException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public Product formProduct(ProductParamsDto params) {
        return Product.builder()
                .id(params.id)
                .title(params.title)
                .price(params.price)
                .discountType(discountService.findById(params.discount_id))
                .build();
    }
}
