package ru.clevertec.tasks.olga.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class DiscountCard extends AbstractModel {
    private LocalDate birthday;
    private DiscountType discountType;

    public DiscountCard(){super();}

    @Builder
    public DiscountCard(long id, LocalDate birthday, DiscountType discountType){
        super(id);
        this.birthday = birthday;
        this.discountType = discountType;
    }
}
