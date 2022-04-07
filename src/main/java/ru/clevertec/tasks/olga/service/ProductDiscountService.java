package ru.clevertec.tasks.olga.service;

import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.dto.ProductDiscountDTO;
import ru.clevertec.tasks.olga.exception.service.ServiceException;

public interface ProductDiscountService extends CRUDService<ProductDiscountType, ProductDiscountDTO> {
    ProductDiscountType formDiscount(ProductDiscountDTO dto) throws ServiceException;
}
