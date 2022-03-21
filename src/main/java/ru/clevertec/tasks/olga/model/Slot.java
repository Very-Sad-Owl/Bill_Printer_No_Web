package ru.clevertec.tasks.olga.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.decimal4j.util.DoubleRounder;

@Getter
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class Slot extends AbstractModel {
    private Product product;
    private int quantity;
    private double totalPrice;

    public Slot(){super();}

    @Builder
    public Slot(long id, Product product, int quantity, double totalPrice) {
        super(id);
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Slot(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        recalculatePrice();
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
        recalculatePrice();
    }

    public void setProduct(Product product){
        this.product = product;
        recalculatePrice();

    }

    public double getTotalPrice() {
        return DoubleRounder.round(totalPrice, 2);
    }

    public double getRawPrice(){
        return DoubleRounder.round((product.getPrice() * quantity), 2);
    }


    private void recalculatePrice(){
        if (product != null) {
            totalPrice = product.getPrice() * quantity;
            if (product.getDiscountType().isActive(quantity)) {
                totalPrice -= totalPrice * product.getDiscountType().getDiscount() / 100;
            }
        }
    }

}
