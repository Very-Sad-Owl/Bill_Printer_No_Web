package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.exception.CashierNotFoundException;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.repository.CashierRepository;
import ru.clevertec.tasks.olga.repository.impl.CashierRepositoryImpl;
import ru.clevertec.tasks.olga.service.CashierService;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class CashierServiceImpl extends AbstractService<Cashier, CashierRepository> implements CashierService {

    private CashierRepository cashierRepository = new CashierRepositoryImpl();


    @Override
    public long save(Cashier cashier) {
        return cashierRepository.save(cashier);
    }

    @Override
    public Cashier findById(long id) {
        Optional<Cashier> cashier = cashierRepository.findById(id);
        if (cashier.isPresent()) {
            return cashier.get();
        } else {
            throw new CashierNotFoundException("error.cashier_not_found");
        }
    }

    @Override
    public List<Cashier> getAll() {
        return null;
    }

    @Override
    public boolean delete(Cashier cashier, String filePath) {
        return false;
    }

    @Override
    public Cashier update(Cashier cashier, String filePath) {
        return null;
    }
}
