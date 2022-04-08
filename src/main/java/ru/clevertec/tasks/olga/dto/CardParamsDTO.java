package ru.clevertec.tasks.olga.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class CardParamsDTO extends AbstractDto{
    public String birthday;
    @JsonProperty("discount_id")
    public long discountId;
}
