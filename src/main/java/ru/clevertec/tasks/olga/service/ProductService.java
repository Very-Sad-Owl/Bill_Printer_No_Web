package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.service.CRUDService;

public interface ProductService extends CRUDService<Product> {
    Product formProduct(ProductParamsDto params);
}
