package ru.clevertec.tasks.olga.service.impl;


import ru.clevertec.tasks.olga.exception.ProductNotFoundException;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.repository.impl.ProductRepositoryImpl;
import ru.clevertec.tasks.olga.service.ProductService;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class ProductServiceImpl extends AbstractService<Product, ProductRepository> implements ProductService {

    private ProductRepository productRepository = new ProductRepositoryImpl();

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
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    public boolean delete(Product product, String filePath) {
        return false;
    }

    @Override
    public Product update(Product product, String filePath) {
        return null;
    }
}
