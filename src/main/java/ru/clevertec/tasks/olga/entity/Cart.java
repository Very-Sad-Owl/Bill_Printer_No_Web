package ru.clevertec.tasks.olga.entity;

import lombok.*;
import org.decimal4j.util.DoubleRounder;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;

import java.util.List;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
public class Cart extends AbstractModel {
    private List<Slot> positions;
    private DiscountCard discountCard;
    private Cashier cashier;
    private double price;

    public Cart(){super();}

    @Builder
    public Cart(long id, List<Slot> positions, DiscountCard discountCard, Cashier cashier, double price){
        super(id);
        this.positions = positions;
        this.discountCard = discountCard;
        this.cashier = cashier;
        this.price = price;
    }

    public Cart(long id, List<Slot> positions, DiscountCard discountCard, Cashier cashier){
        super(id);
        this.positions = positions;
        this.discountCard = discountCard;
        this.cashier = cashier;
//        calculatePrice();
    }

    public Cart(List<Slot> positions, DiscountCard discountCard, Cashier cashier){
        this.positions = positions;
        this.discountCard = discountCard;
        this.cashier = cashier;
//        calculatePrice();
    }

    public void calculatePrice(){
        price = 0;
        if (positions != null) {
            for (Slot slot : positions) {
                price += slot.getTotalPrice();
            }
            price = (price * (100 - discountCard.getCardType().getDiscount())) / 100;
        }
    }

    public long getId(){
        return super.getId();
    }

    public void setPositions(List<Slot> positions) {
        this.positions = positions;
//        calculatePrice();
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
//        calculatePrice();
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(long id){
        super.setId(id);
    }

    public double getPrice() {
        return DoubleRounder.round(price, 2);
    }

    public void addSlot(Slot slot){
        if (positions == null){
            positions = new ArrayListImpl<>();
        }
        positions.add(slot);
//        calculatePrice();
    }

    public double getRawPrice(){
        if (positions == null) {return 0;}
        double rawPrice = 0;
        for (Slot position : positions){
            rawPrice += position.getRawPrice();
        }
        return DoubleRounder.round(rawPrice, 2);
    }

    public double getTotalDiscount(){
        return DoubleRounder.round(((getRawPrice() - price) * 100) / getRawPrice(), 2);
    }
}
