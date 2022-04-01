package ru.clevertec.tasks.olga.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class ProductParamsDto extends AbstractDto{
    public String title;
    public double price;
    public long discount_id;
}
