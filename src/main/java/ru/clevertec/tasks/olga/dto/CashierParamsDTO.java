package ru.clevertec.tasks.olga.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper=true)
public class CashierParamsDTO extends AbstractDto{
    public String name;
    public String surname;
}
