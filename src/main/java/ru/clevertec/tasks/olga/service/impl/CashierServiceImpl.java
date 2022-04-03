package ru.clevertec.tasks.olga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.exception.CashierNotFoundExceptionCustom;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.repository.CashierRepository;
import ru.clevertec.tasks.olga.service.CashierService;

import java.util.List;
import java.util.Optional;

@Service
public class CashierServiceImpl
        extends AbstractService
        implements CashierService {

    private final CashierRepository cashierRepository;

    @Autowired
    public CashierServiceImpl(CashierRepository cashierRepository) {
        this.cashierRepository = cashierRepository;
    }

    @Override
    public Cashier save(CashierParamsDTO dto) {
        Cashier cashier = formCashier(dto);
        long insertedId = cashierRepository.save(cashier);
        cashier.setId(insertedId);
        return cashier;
    }

    @Override
    public Cashier findById(long id) {
        Optional<Cashier> cashier = cashierRepository.findById(id);
        if (cashier.isPresent()) {
            return cashier.get();
        } else {
            throw new CashierNotFoundExceptionCustom("error.cashier_not_found");
        }
    }

    @Override
    public List<Cashier> getAll(int limit, int offset) {
        List<Cashier> found = cashierRepository.getAll(limit, offset);
        if (found.isEmpty()){
            throw new CashierNotFoundExceptionCustom();
        } else {
            return found;
        }
    }

    @Override
    public boolean delete(long id) {
        return cashierRepository.delete(id);
    }

    @Override
    public Cashier update(CashierParamsDTO dto) {
        Cashier original = findById(dto.id);
        CashierParamsDTO newCashier = CashierParamsDTO.builder()
                .id(dto.id)
                .name(dto.name == null ? original.getName() : dto.name)
                .surname(dto.surname == null ? original.getSurname() : dto.surname)
                .build();
        Cashier updated = formCashier(newCashier);
        if (cashierRepository.update(updated)){
            return updated;
        } else {
            throw new CashierNotFoundExceptionCustom();
        }
    }

    @Override
    public Cashier formCashier(CashierParamsDTO params) {
        return Cashier.builder()
                .id(params.id)
                .name(params.name)
                .surname(params.surname)
                .build();
    }
}
