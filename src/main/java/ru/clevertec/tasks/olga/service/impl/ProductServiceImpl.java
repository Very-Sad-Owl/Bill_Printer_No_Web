package ru.clevertec.tasks.olga.service.impl;


import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.service.ProductService;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ProductServiceImpl extends AbstractService<Product, ProductRepository> implements ProductService {

    private ProductRepository productRepository = repositoryFactory.getProductRepository();

    @Override
    public void save(Product product, String fileName) {

    }

    @Override
    public Product findById(long id, String filePath) {
        return productRepository.findById(id, filePath);
    }

    @Override
    public List<Product> getAll(String filePath) {
        return productRepository.getAll(filePath);
    }
}
