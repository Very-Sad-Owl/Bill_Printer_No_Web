package ru.clevertec.tasks.olga.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper=true)
public class CardTypeDto extends AbstractDto{
    public long id;
    public String title;
    public double discountVal;
}
