package ru.clevertec.tasks.olga.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.exception.crud.notfound.BillNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.CardNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.CashierNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.crud.notfound.ProductNotFoundExceptionHandled;
import ru.clevertec.tasks.olga.exception.repository.RepositoryException;
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
    public Cashier save(CashierParamsDTO dto) {
        validateFullyFilledDto(dto);
        try {
            Cashier cashier = formCashier(dto);
            long insertedId = cashierRepository.save(cashier);
            cashier.setId(insertedId);
            return cashier;
        } catch (RepositoryException e) {
            throw new CardNotFoundExceptionHandled(e);
        }
    }

    @Override
    @SneakyThrows
    public Cashier findById(long id) {
        validateId(id);
        try {
            Optional<Cashier> cashier = cashierRepository.findById(id);
            if (cashier.isPresent()) {
                return cashier.get();
            } else {
                throw new CashierNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<Cashier> getAll(int limit, int offset) {
        return cashierRepository.getAll(limit, offset);
    }

    @Override
    public void delete(long id) {
        validateId(id);
        try {
            if (!cashierRepository.delete(id)) {
                throw new BillNotFoundExceptionHandled(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e);
        }
    }

    @Override
    public Cashier patch(CashierParamsDTO dto) {
        validatePartlyFilledObject(dto);
        Cashier original;
        Cashier updated;
        try {
            original = findById(dto.id);
            CashierParamsDTO newCashier = CashierParamsDTO.builder()
                    .id(dto.id)
                    .name(dto.name == null ? original.getName() : dto.name)
                    .surname(dto.surname == null ? original.getSurname() : dto.surname)
                    .build();
            updated = formCashier(newCashier);
        } catch (NotFoundExceptionHandled e) {
            throw new UpdatingExceptionHandled(e);
        }
        try {
            cashierRepository.update(updated);
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
        }
    }

    @Override
    public Cashier put(CashierParamsDTO dto) {
        validateFullyFilledDto(dto);
        Cashier updated = formCashier(dto);
        try {
            if (!cashierRepository.update(updated)) {
                throw new UpdatingExceptionHandled(new CashierNotFoundExceptionHandled(dto.id + ""));
            }
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedExceptionHandled(e.getMessage());
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
