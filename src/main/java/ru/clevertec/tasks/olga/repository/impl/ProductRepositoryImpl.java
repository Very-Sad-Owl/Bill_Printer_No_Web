package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;
import ru.clevertec.tasks.olga.util.tablemapper.WorkerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    private static final NodeWorker<Product> productWorker = WorkerFactory.getInstance().getProductWorker();

    @Override
    public long save(Product product) {
        try {
            return CRUDHelper.save(product, INSERT_PRODUCT, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public Optional<Product> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_PRODUCT_BY_ID, id, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public List<Product> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_PRODUCTS, productWorker, limit, offset);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.connection");
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            return CRUDHelper.update(product, UPDATE_PRODUCT, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            return CRUDHelper.delete(DELETE_PRODUCT, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }
}
