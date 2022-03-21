package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.model.Cashier;
import ru.clevertec.tasks.olga.repository.CashierRepository;
import ru.clevertec.tasks.olga.repository.common.DbHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import ru.clevertec.tasks.olga.util.orm.WorkerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
public class CashierRepositoryImpl implements CashierRepository {

    private static final NodeWorker<Cashier> cashierWorker = WorkerFactory.getInstance().getCashierWorker();

    @Override
    public long save(Cashier cashier) {
        try {
            return DbHelper.save(cashier, INSERT_CASHIER, cashierWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public Optional<Cashier> findById(long id) {
        try {
            return DbHelper.findById(FIND_CASHIER_BY_ID, id, cashierWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public List<Cashier> getAll() {
        try {
            return DbHelper.getAll(GET_CASHIERS, cashierWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public boolean update(Cashier cashier) {
        try {
            return DbHelper.update(cashier, UPDATE_CASHIER, cashierWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            return DbHelper.delete(DELETE_CASHIER, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("");
        }
    }
}
