package ru.clevertec.tasks.olga.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.exception.repoexc.RepositoryException;
import ru.clevertec.tasks.olga.exception.serviceexc.DeletionExceptionHandled;
import ru.clevertec.tasks.olga.exception.serviceexc.NotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.serviceexc.SavingExceptionHandled;
import ru.clevertec.tasks.olga.exception.serviceexc.ServiceException;
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
    @SneakyThrows
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
            return null;
        }
    }

    @Override
    @SneakyThrows
    public List<Cashier> getAll(int limit, int offset) {
        return cashierRepository.getAll(limit, offset);
    }

    @Override
    @SneakyThrows
    public void delete(long id) {
        cashierRepository.delete(id);
    }

    @Override
    @SneakyThrows
    public Cashier update(CashierParamsDTO dto) {
        Cashier original = findById(dto.id);
        CashierParamsDTO newCashier = CashierParamsDTO.builder()
                .id(dto.id)
                .name(dto.name == null ? original.getName() : dto.name)
                .surname(dto.surname == null ? original.getSurname() : dto.surname)
                .build();
        Cashier updated = formCashier(newCashier);
        cashierRepository.update(updated);
        return updated;
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
