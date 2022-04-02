package ru.clevertec.tasks.olga.service;


import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;

public interface CashierService extends CRUDService<Cashier, CashierParamsDTO> {
    Cashier formCashier(CashierParamsDTO params);
}
