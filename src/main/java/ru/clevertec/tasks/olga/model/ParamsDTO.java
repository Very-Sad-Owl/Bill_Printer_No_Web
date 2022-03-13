package ru.clevertec.tasks.olga.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ParamsDTO {
    private Map<Long, Integer> goods = new HashMap<>();
    private long cashier_id;
    private long card_id;
    private long bill_id;
    private String dataPath;
    private String action;
}
