package ru.clevertec.tasks.olga.service.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.tasks.olga.exception.ProductNotFoundException;
import ru.clevertec.tasks.olga.model.Product;
import ru.clevertec.tasks.olga.model.ProductDiscountType;
import ru.clevertec.tasks.olga.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceImplTest {

    private static final String DB_PATH = System.getProperty("user.dir")+"\\db\\test_db\\";
    private ProductService productService = new ProductServiceImpl();

    @Test
    void findById_existingNode_productObj() {
        int  id = 1;
        Product expected = new Product(id, "milk", 1.43,
                ProductDiscountType.builder().title("MORE_THAN_FIVE").discount(10).requiredMinQuantity(5).build());

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
        List<Product> expected = new ArrayList<>();
        expected.add(new Product(1, "bread", 1.43,
                ProductDiscountType.builder().title("NONE").discount(0).requiredMinQuantity(0).id(1).build()));
        expected.add(new Product(2, "milk", 1.2,
                ProductDiscountType.builder().title("MORE_THAN_FIVE").discount(10).requiredMinQuantity(5).id(2).build()));
        expected.add(new Product(3, "sblrok", 0.5,
                ProductDiscountType.builder().title("TWO_AS_ONE").discount(50).requiredMinQuantity(2).id(3).build()));
        List<Product> actual = productService.getAll();

        assertEquals(expected, actual);
    }
}