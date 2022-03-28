package ru.clevertec.tasks.olga.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper=true)
public class CardParamsDTO extends AbstractDto{
    public long id;
    public String birthday;
    public long discountId;
}
