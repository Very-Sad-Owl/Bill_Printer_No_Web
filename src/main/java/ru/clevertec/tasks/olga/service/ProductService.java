package ru.clevertec.tasks.olga.service;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.dto.ProductParamsDto;
import ru.clevertec.tasks.olga.exception.serviceexc.ServiceException;

public interface ProductService extends CRUDService<Product, ProductParamsDto> {
    Product formProduct(ProductParamsDto params);
}
