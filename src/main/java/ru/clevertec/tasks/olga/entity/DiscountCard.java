package ru.clevertec.tasks.olga.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class DiscountCard extends AbstractModel {
    private LocalDate birthday;
    private CardType cardType;

    public DiscountCard(){super();}

    @Builder
    public DiscountCard(long id, LocalDate birthday, CardType cardType){
        super(id);
        this.birthday = birthday;
        this.cardType = cardType;
    }
}
