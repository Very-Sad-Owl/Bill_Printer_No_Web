package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.exception.ReadingExceptionCustom;
import ru.clevertec.tasks.olga.exception.WritingExceptionCustom;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final NodeWorker<Product> productWorker;

    @Autowired
    public ProductRepositoryImpl(NodeWorker<Product> productWorker) {
        this.productWorker = productWorker;
    }

    @Override
    public long save(Product product) {
        try {
            return CRUDHelper.save(product, INSERT_PRODUCT, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingExceptionCustom("error.writing");
        }
    }

    @Override
    public Optional<Product> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_PRODUCT_BY_ID, id, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new ReadingExceptionCustom("error.reading");
        }
    }

    @Override
    public List<Product> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_PRODUCTS, productWorker, limit, offset);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new ReadingExceptionCustom("error.connection");
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            return CRUDHelper.update(product, UPDATE_PRODUCT, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingExceptionCustom("error.writing");
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            return CRUDHelper.delete(DELETE_PRODUCT, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingExceptionCustom("error.writing");
        }
    }
}
