package ru.clevertec.tasks.olga.model;

public enum ProductDiscountType {

    NONE(0., 0), MORE_THAN_FIVE(10., 5), TWO_AS_ONE(50., 2);

    private double discount;
    private int requiredMinQuantity;

    ProductDiscountType(double discount, int requiredMinQuantity){
        this.discount = discount;
        this.requiredMinQuantity = requiredMinQuantity;
    }

    public double getDiscount(){
        return discount;
    }

    public boolean isActive(int quantity){
        return quantity >= requiredMinQuantity;
    }
}
