package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.annotation.UseCache;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ModelRowMapper<Product> productWorker;

    @Autowired
    public ProductRepositoryImpl(ModelRowMapper<Product> productWorker) {
        this.productWorker = productWorker;
    }

    @Override
    @UseCache
    public long save(Product product) throws RepositoryException {
        try {
            return CRUDHelper.save(product, INSERT_PRODUCT, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    @UseCache
    public Optional<Product> findById(long id) throws RepositoryException {
        try {
            return CRUDHelper.findById(FIND_PRODUCT_BY_ID, id, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    public List<Product> getAll(int limit, int offset) throws RepositoryException {
        try {
            return CRUDHelper.getAll(GET_PRODUCTS, productWorker, limit, offset);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    @UseCache
    public boolean update(Product product) throws RepositoryException {
        try {
            return CRUDHelper.update(product, UPDATE_PRODUCT, productWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    @UseCache
    public boolean delete(long id) throws RepositoryException {
        try {
            return CRUDHelper.delete(DELETE_PRODUCT, id);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }
}
