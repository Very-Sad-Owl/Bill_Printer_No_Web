package ru.clevertec.tasks.olga.model;

public enum DiscountType {

    NONE(0.), BRONZE(3.), SILVER(5.), GOLD(7.);

    private double discount;

    DiscountType(double discount){
        this.discount = discount;
    }

    public double getDiscount(){
        return discount;
    }
}
