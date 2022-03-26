package ru.clevertec.tasks.olga.service.impl;


import ru.clevertec.tasks.olga.exception.ProductNotFoundException;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.repository.impl.ProductRepositoryImpl;
import ru.clevertec.tasks.olga.service.ProductDiscountService;
import ru.clevertec.tasks.olga.service.ProductService;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class ProductServiceImpl extends AbstractService<Product, ProductRepository> implements ProductService {

    private static final ProductRepository productRepository = new ProductRepositoryImpl();
    private static final ProductDiscountService discountService = new ProductDiscountImpl();

    @Override
    public long save(Product product) {
        return productRepository.save(product);
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
    public Product update(long id, Product product) {
        return null;
    }

    @Override
    public Product formProduct(ProductParamsDto params) {
        return Product.builder()
                .title(params.title)
                .price(params.price)
                .discountType(discountService.findById(params.discount_id))
                .build();
    }
}
