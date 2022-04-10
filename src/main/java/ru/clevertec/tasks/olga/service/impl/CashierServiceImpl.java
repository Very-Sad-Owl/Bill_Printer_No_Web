package ru.clevertec.tasks.olga.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.dto.CashierParamsDTO;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.exception.crud.notfound.*;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
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
            throw new CardNotFoundException(e);
        }
    }

    @Override
    public Cashier findById(long id) {
        validateId(id);
        try {
            Optional<Cashier> cashier = cashierRepository.findById(id);
            if (cashier.isPresent()) {
                return cashier.get();
            } else {
                throw new CashierNotFoundException(id + "");
            }
        } catch (RepositoryException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<Cashier> getAll(Pageable pageable) {
        return cashierRepository.getAll(pageable);
    }

    @Override
    public void delete(long id) {
        validateId(id);
        try {
            if (!cashierRepository.delete(id)) {
                throw new DeletionException(new CashierNotFoundException(id + ""));
            }
        } catch (RepositoryException e) {
            throw new UndefinedException(e);
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
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        }
        try {
            cashierRepository.update(updated);
            return updated;
        } catch (RepositoryException e) {
            throw new UndefinedException(e.getMessage());
        }
    }

    @Override
    public Cashier put(CashierParamsDTO dto) {
        validateFullyFilledDto(dto);
        Cashier updated = formCashier(dto);
        try {
            if (!cashierRepository.update(updated)) {
                throw new UpdatingException(new CashierNotFoundException(dto.id + ""));
            }
            return updated;
        } catch (NotFoundException e) {
            throw new UpdatingException(e);
        } catch (RepositoryException e) {
            throw new UndefinedException(e.getMessage());
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
