package ru.clevertec.tasks.olga.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartParamsDTO extends AbstractDto{
    public Map<Long, Integer> goods = new HashMap<>();
    public long id;
    public long cashier_id;
    public long card_id;
}
