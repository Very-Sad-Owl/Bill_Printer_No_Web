package ru.clevertec.tasks.olga.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper=true)
public class ProductParamsDto extends AbstractDto{
    public long id;
    public String title;
    public double price;
    public long discount_id;
}
