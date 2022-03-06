package ru.clevertec.tasks.olga.service.impl;


import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.repository.models_repo.ProductRepository;
import ru.clevertec.tasks.olga.service.models_service.ProductService;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ProductServiceImpl extends AbstractService<Product, ProductRepository> implements ProductService {

    private ProductRepository productRepository = repositoryFactory.getProductRepository();

    @Override
    public void save(Product product) {

    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }
}
