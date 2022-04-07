package ru.clevertec.tasks.olga.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.exception.handeled.NotFoundExceptionHandled;
import ru.clevertec.tasks.olga.repository.CashierRepository;
import ru.clevertec.tasks.olga.service.CashierService;

import java.util.List;
import java.util.Optional;
import static ru.clevertec.tasks.olga.util.validation.CRUDParamsValidator.*;

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
        validateDtoForSave(dto);
        Cashier cashier = formCashier(dto);
        long insertedId = cashierRepository.save(cashier);
        cashier.setId(insertedId);
        return cashier;
    }

    @Override
    @SneakyThrows
    public Cashier findById(long id) {
        validateId(id);
        Optional<Cashier> cashier = cashierRepository.findById(id);
        if (cashier.isPresent()) {
            return cashier.get();
        } else {
            throw new NotFoundExceptionHandled();
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
        validateId(id);
        cashierRepository.delete(id);
    }

    @Override
    @SneakyThrows
    public Cashier update(CashierParamsDTO dto) {
        validatePartlyFilledObject(dto);
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
