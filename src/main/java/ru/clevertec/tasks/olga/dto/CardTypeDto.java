package ru.clevertec.tasks.olga.dto;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class CardTypeDto extends AbstractDto{
    public String title;
    @SerializedName("discount")
    public double discountVal;
}
