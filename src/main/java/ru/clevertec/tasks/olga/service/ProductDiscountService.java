package ru.clevertec.tasks.olga.service;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.dto.ProductDiscountDTO;

public interface ProductDiscountService extends CRUDService<ProductDiscountType, ProductDiscountDTO> {
    ProductDiscountType formDiscount(ProductDiscountDTO dto);
}
