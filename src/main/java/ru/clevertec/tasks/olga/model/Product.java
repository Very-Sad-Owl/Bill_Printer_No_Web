package ru.clevertec.tasks.olga.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
public class Product extends AbstractModel {

    private String title;
    private double price;
    private ProductDiscountType discountType;

    public Product(){super();}

    public Product(long id, String title, double price, ProductDiscountType discountType){
        super(id);
        this.title = title;
        this.price = price;
        this.discountType = discountType;
    }
}
