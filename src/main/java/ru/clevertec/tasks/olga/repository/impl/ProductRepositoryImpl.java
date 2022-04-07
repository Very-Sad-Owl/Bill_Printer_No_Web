package ru.clevertec.tasks.olga.repository.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.annotation.UseCache;
import ru.clevertec.tasks.olga.exception.handeled.ReadingException;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.exception.handeled.DeletionExceptionHandled;
import ru.clevertec.tasks.olga.exception.handeled.SavingExceptionHandled;
import ru.clevertec.tasks.olga.exception.handeled.UpdatingExceptionHandled;
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
    @UseCache
    @SneakyThrows
    public long save(Product product) {
        try {
            return CRUDHelper.save(product, INSERT_PRODUCT, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new SavingExceptionHandled(e);
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public Optional<Product> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_PRODUCT_BY_ID, id, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e);
        }
    }

    @Override
    @SneakyThrows
    public List<Product> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_PRODUCTS, productWorker, limit, offset);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e);
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public boolean update(Product product) {
        try {
            return CRUDHelper.update(product, UPDATE_PRODUCT, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new UpdatingExceptionHandled(e);
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public boolean delete(long id) {
        try {
            return CRUDHelper.delete(DELETE_PRODUCT, id);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DeletionExceptionHandled(e);
        }
    }
}
