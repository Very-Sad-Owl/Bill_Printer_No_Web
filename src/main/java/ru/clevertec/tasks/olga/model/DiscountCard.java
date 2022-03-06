package ru.clevertec.tasks.olga.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class DiscountCard extends AbstractModel {
    private Date birthday;
    private DiscountType discountType;

    public DiscountCard(){super();}

    public DiscountCard(long id, Date birthday, DiscountType discountType){
        super(id);
        this.birthday = birthday;
        this.discountType = discountType;
    }
}
