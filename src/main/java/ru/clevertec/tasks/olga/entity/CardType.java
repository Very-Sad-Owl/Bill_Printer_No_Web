package ru.clevertec.tasks.olga.entity;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
public class CardType extends AbstractModel{
    private double discount;
    private String title;

    public CardType(double discount, String title) {
        this.discount = discount;
        this.title = title;
    }

    @Builder
    public CardType(long id, double discount, String title) {
        super(id);
        this.discount = discount;
        this.title = title;
    }
}
