package ru.clevertec.tasks.olga.model.dto;

import lombok.Builder;
import java.util.Map;

@Builder
public class CartDto {
    public long id;
    public Map<Long, Integer> goods;
    public long cashierId;
    public long discountCardId;
    public double price;
}
