package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.exception.ReadingExceptionCustom;
import ru.clevertec.tasks.olga.exception.WritingExceptionCustom;
import ru.clevertec.tasks.olga.entity.Cashier;
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
    public long save(Cashier cashier) {
        try {
            return CRUDHelper.save(cashier, INSERT_CASHIER, cashierWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new WritingExceptionCustom("error.writing");
        }
    }

    @Override
    public Optional<Cashier> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_CASHIER_BY_ID, id, cashierWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingExceptionCustom("error.reading");
        }
    }

    @Override
    public List<Cashier> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_CASHIERS, cashierWorker, limit, offset);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new WritingExceptionCustom("error.writing");
        }
    }

    @Override
    public boolean update(Cashier cashier) {
        try {
            return CRUDHelper.update(cashier, UPDATE_CASHIER, cashierWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingExceptionCustom("error.writing");
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            return CRUDHelper.delete(DELETE_CASHIER, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingExceptionCustom("");
        }
    }
}
