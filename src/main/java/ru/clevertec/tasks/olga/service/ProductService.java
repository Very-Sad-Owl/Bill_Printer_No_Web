package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;

public interface ProductService extends CRUDService<Product, ProductParamsDto> {
    Product formProduct(ProductParamsDto params);
}
