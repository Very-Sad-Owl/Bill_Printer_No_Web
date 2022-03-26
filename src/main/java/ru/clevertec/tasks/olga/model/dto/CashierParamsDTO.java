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
public class CashierParamsDTO extends AbstractDto{
    public long id;
    public String name;
    public String surname;
}
