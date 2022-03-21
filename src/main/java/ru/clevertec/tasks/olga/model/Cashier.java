package ru.clevertec.tasks.olga.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
public class Cashier extends AbstractModel {
    private String name;
    private String surname;

    public Cashier(){super();}

    @Builder
    public Cashier(long id, String name, String surname){
        super(id);
        this.name = name;
        this.surname = surname;
    }

    public String getFullName(){
        return surname + " " + name;
    }
}
