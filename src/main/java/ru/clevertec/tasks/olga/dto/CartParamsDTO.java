package ru.clevertec.tasks.olga.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class CartParamsDTO extends AbstractDto{
    public long cashier_uid;
    public long card_uid;
    public Map<Long, Integer> products = new HashMap<>();
}
