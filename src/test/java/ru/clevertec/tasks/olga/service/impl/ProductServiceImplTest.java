package ru.clevertec.tasks.olga.service.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.NoRequiredArgsException;
import ru.clevertec.tasks.olga.exception.ProductNotFoundException;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.ProductDiscountType;
import ru.clevertec.tasks.olga.service.ProductService;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceImplTest {

    private ProductService productService = new ProductServiceImpl();

    @Test
    void findById_existingNode_productObj() {
        int  id = 1;
        Product expected = new Product(id, "milk", 1.43,
                ProductDiscountType.valueOf("more_than_five".toUpperCase()));

        Product actual = productService.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    void findBuId_nonexistingNode_exception(){
        int id = -1;
        assertThrows(ProductNotFoundException.class, () -> {
            productService.findById(id);
        });
    }

    @Test
    void getAll() {
        List<Product> expected = new ArrayListImpl<>();
        expected.add(new Product(1, "milk", 1.43,
                ProductDiscountType.valueOf("more_than_five".toUpperCase())));
        expected.add(new Product(2, "bread", 1.20,
                ProductDiscountType.valueOf("none".toUpperCase())));

        List<Product> actual = productService.getAll();

        assertEquals(expected, actual);
    }
}