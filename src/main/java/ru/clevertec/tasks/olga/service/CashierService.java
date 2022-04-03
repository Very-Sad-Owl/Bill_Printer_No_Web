package ru.clevertec.tasks.olga.service;


import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;

public interface CashierService extends CRUDService<Cashier, CashierParamsDTO> {
    Cashier formCashier(CashierParamsDTO params);
}
