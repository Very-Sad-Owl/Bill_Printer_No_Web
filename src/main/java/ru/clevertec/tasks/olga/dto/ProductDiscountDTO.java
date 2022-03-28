package ru.clevertec.tasks.olga.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper=true)
public class ProductDiscountDTO extends AbstractDto{
    public long id;
    public String title;
    public double val;
    public int requiredQuantity;
}
