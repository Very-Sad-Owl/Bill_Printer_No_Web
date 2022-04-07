package ru.clevertec.tasks.olga.service.impl;


import com.google.common.base.Defaults;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.exception.handeled.NotFoundExceptionHandled;
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
    @SneakyThrows
    public Product save(ProductParamsDto dto) {
        validateDtoForSave(dto);
        Product product = formProduct(dto);
        long insertedId = productRepository.save(product);
        product.setId(insertedId);
        return product;
    }

    @Override
    @SneakyThrows
    public Product findById(long id) {
        validateId(id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new NotFoundExceptionHandled();
        }
    }

    @Override
    @SneakyThrows
    public List<Product> getAll(int limit, int offset) {
        return productRepository.getAll(limit, offset);
    }

    @Override
    @SneakyThrows
    public void delete(long id) {
        validateId(id);
        productRepository.delete(id);
    }

    @Override
    @SneakyThrows
    public Product update(ProductParamsDto params) {
        validatePartlyFilledObject(params);
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
        Product updated = formProduct(newProduct);
        productRepository.update(updated);
        return updated;
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
