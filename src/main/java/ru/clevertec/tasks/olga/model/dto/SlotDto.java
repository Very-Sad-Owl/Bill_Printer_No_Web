package ru.clevertec.tasks.olga.model.dto;

import lombok.Builder;

@Builder
public class SlotDto {
    public long id;
    public long product_id;
    public int quantity;
    public double price;
    public long cart_id;
}
