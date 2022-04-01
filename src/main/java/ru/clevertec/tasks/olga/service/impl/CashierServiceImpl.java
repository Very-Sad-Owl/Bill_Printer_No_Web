package ru.clevertec.tasks.olga.service.impl;

import ru.clevertec.tasks.olga.exception.CashierNotFoundException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.repository.CashierRepository;
import ru.clevertec.tasks.olga.service.CashierService;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class CashierServiceImpl
        extends AbstractService<Cashier, CashierParamsDTO, CashierRepository>
        implements CashierService {

    private static final CashierRepository cashierRepository = repoFactory.getCashierRepository();

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
            throw new CashierNotFoundException("error.cashier_not_found");
        }
    }

    @Override
    public List<Cashier> getAll(int limit, int offset) {
        List<Cashier> found = cashierRepository.getAll(limit, offset);
        if (found.isEmpty()){
            throw new CashierNotFoundException();
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
        if (original == updated) throw new WritingException(); //TODO: nothing to update exception
        if (cashierRepository.update(updated)){
            return updated;
        } else {
            throw new CashierNotFoundException();
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
