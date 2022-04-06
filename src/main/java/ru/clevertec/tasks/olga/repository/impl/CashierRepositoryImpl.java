package ru.clevertec.tasks.olga.repository.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.annotation.UseCache;
import ru.clevertec.tasks.olga.exception.repoexc.ConnectionException;
import ru.clevertec.tasks.olga.exception.repoexc.ReadingException;
import ru.clevertec.tasks.olga.exception.repoexc.RepositoryException;
import ru.clevertec.tasks.olga.exception.repoexc.WritingException;
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
    @UseCache
    @SneakyThrows
    public long save(Cashier cashier) {
        try {
            return CRUDHelper.save(cashier, INSERT_CASHIER, cashierWorker);
        } catch (SQLException e) {
            throw new WritingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public Optional<Cashier> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_CASHIER_BY_ID, id, cashierWorker);
        } catch (SQLException e) {
            throw new ReadingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @SneakyThrows
    public List<Cashier> getAll(int limit, int offset) throws RepositoryException {
        try {
            return CRUDHelper.getAll(GET_CASHIERS, cashierWorker, limit, offset);
        } catch (SQLException e) {
            throw new ReadingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public boolean update(Cashier cashier) throws RepositoryException {
        try {
            return CRUDHelper.update(cashier, UPDATE_CASHIER, cashierWorker);
        } catch (SQLException e) {
            throw new WritingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public boolean delete(long id) throws RepositoryException {
        try {
            return CRUDHelper.delete(DELETE_CASHIER, id);
        } catch (SQLException e) {
            throw new WritingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }
}
