package ru.clevertec.tasks.olga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("discount")
    public double val;
    @JsonProperty("required_quantity")
    public int requiredQuantity;
}
