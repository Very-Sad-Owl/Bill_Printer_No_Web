package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.exception.repository.ReadingException;
import ru.clevertec.tasks.olga.exception.repository.RepositoryException;
import ru.clevertec.tasks.olga.exception.repository.WritingException;
import ru.clevertec.tasks.olga.repository.CashierRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
@Repository
public class CashierRepositoryImpl implements CashierRepository {

    private final NodeWorker<Cashier> cashierWorker;

    @Autowired
    public CashierRepositoryImpl(NodeWorker<Cashier> cashierWorker) {
        this.cashierWorker = cashierWorker;
    }

    @Override
    public long save(Cashier cashier) throws RepositoryException {
        try {
            return CRUDHelper.save(cashier, INSERT_CASHIER, cashierWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    public Optional<Cashier> findById(long id) throws RepositoryException {
        try {
            return CRUDHelper.findById(FIND_CASHIER_BY_ID, id, cashierWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    public List<Cashier> getAll(int limit, int offset) throws RepositoryException {
        try {
            return CRUDHelper.getAll(GET_CASHIERS, cashierWorker, limit, offset);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    public boolean update(Cashier cashier) throws RepositoryException {
        try {
            return CRUDHelper.update(cashier, UPDATE_CASHIER, cashierWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        try {
            return CRUDHelper.delete(DELETE_CASHIER, id);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }
}
