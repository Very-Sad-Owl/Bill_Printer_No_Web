package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.repository.models_repo.CashierRepository;
import ru.clevertec.tasks.olga.service.models_service.CashierService;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CashierServiceImpl extends AbstractService<Cashier, CashierRepository> implements CashierService {

    private CashierRepository cashierRepository = repositoryFactory.getCashierRepository();


    @Override
    public void save(Cashier cashier) {

    }

    @Override
    public Cashier findById(long id) {
        return cashierRepository.findById(id);
    }

    @Override
    public List<Cashier> getAll() {
        return cashierRepository.getAll();
    }
}
