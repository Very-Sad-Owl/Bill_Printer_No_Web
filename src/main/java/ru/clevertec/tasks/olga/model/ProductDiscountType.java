package ru.clevertec.tasks.olga.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
public class ProductDiscountType extends AbstractModel {
    private double discount;
    private int requiredMinQuantity;
    private String title;

    public ProductDiscountType(double discount, int requiredMinQuantity, String title) {
        this.discount = discount;
        this.requiredMinQuantity = requiredMinQuantity;
        this.title = title;
    }

    @Builder
    public ProductDiscountType(long id, double discount, int requiredMinQuantity, String title) {
        super(id);
        this.discount = discount;
        this.requiredMinQuantity = requiredMinQuantity;
        this.title = title;
    }

    public boolean isActive(int quantity){
        return quantity >= requiredMinQuantity;
    }
}
