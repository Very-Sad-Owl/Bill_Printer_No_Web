package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.repository.CashierRepository;
import ru.clevertec.tasks.olga.service.CashierService;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CashierServiceImpl extends AbstractService<Cashier, CashierRepository> implements CashierService {

    private CashierRepository cashierRepository = repositoryFactory.getCashierRepository();


    @Override
    public void save(Cashier cashier, String fileName) {

    }

    @Override
    public Cashier findById(long id, String filePath) {
        return cashierRepository.findById(id, filePath);
    }

    @Override
    public List<Cashier> getAll(String filePath) {
        return cashierRepository.getAll(filePath);
    }
}
