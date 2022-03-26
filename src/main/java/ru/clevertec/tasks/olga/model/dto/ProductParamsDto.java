package ru.clevertec.tasks.olga.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductParamsDto extends AbstractDto{
    public long id;
    public String title;
    public double price;
    public long discount_id;
}
