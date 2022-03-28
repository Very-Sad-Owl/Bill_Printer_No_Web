package ru.clevertec.tasks.olga.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper=true)
public class CashierParamsDTO extends AbstractDto{
    public long id;
    public String name;
    public String surname;
}
