package ru.clevertec.tasks.olga.service.impl;


import com.google.common.base.Defaults;
import ru.clevertec.tasks.olga.exception.ProductNotFoundException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.repository.impl.ProductRepositoryImpl;
import ru.clevertec.tasks.olga.service.ProductDiscountService;
import ru.clevertec.tasks.olga.service.ProductService;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class ProductServiceImpl
        extends AbstractService<Product, ProductParamsDto, ProductRepository>
        implements ProductService {

    private static final ProductRepository productRepository = new ProductRepositoryImpl();
    private static final ProductDiscountService discountService = new ProductDiscountImpl();

    @Override
    public Product save(ProductParamsDto dto) {
        Product product = formProduct(dto);
        long insertedId = productRepository.save(product);
        product.setId(insertedId);
        return product;
    }

    @Override
    public Product findById(long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        } else {
            throw new ProductNotFoundException("error.product_not_found");
        }
    }

    @Override
    public List<Product> getAll(int limit, int offset) {
        return productRepository.getAll(limit, offset);
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Product update(ProductParamsDto params) {
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
        if (original == updated) throw new WritingException(); //TODO: nothing to update exception
        if (productRepository.update(updated)) {
            return original;
        } else {
            throw new ProductNotFoundException();
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
