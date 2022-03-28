package ru.clevertec.tasks.olga.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper=true)
public class CartParamsDTO extends AbstractDto{
    public Map<Long, Integer> goods = new HashMap<>();
    public long id;
    public long cashier_id;
    public long card_id;
}
