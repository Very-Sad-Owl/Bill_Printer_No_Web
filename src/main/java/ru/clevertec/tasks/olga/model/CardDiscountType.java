package ru.clevertec.tasks.olga.model;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
public class CardDiscountType extends AbstractModel{
    private double discount;
    private String title;

    public CardDiscountType(double discount, String title) {
        this.discount = discount;
        this.title = title;
    }

    @Builder
    public CardDiscountType(long id, double discount, String title) {
        super(id);
        this.discount = discount;
        this.title = title;
    }
}
