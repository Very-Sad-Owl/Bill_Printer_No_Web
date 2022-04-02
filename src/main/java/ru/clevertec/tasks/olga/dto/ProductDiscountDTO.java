package ru.clevertec.tasks.olga.dto;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class ProductDiscountDTO extends AbstractDto{
    public String title;
    @SerializedName("discount")
    public double val;
    @SerializedName("required_quantity")
    public int requiredQuantity;
}
