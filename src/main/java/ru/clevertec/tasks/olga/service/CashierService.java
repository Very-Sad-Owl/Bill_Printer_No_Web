package ru.clevertec.tasks.olga.service;


import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.model.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.service.CRUDService;

public interface CashierService extends CRUDService<Cashier> {
    Cashier formCashier(CashierParamsDTO params);
}
