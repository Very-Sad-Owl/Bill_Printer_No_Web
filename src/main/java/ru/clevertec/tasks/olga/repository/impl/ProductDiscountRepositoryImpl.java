package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.ProductDiscountRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;


@Slf4j
@Repository
public class ProductDiscountRepositoryImpl implements ProductDiscountRepository {

    private final ModelRowMapper<ProductDiscountType> discountWorker;

    @Autowired
    public ProductDiscountRepositoryImpl(ModelRowMapper<ProductDiscountType> discountWorker) {
        this.discountWorker = discountWorker;
    }

    @Override
    public long save(ProductDiscountType discountCard) throws RepositoryException {
        try {
            return CRUDHelper.save(discountCard, INSERT_PRODUCT_DISCOUNT_TYPE, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    public Optional<ProductDiscountType> findById(long id) throws RepositoryException {
        try {
            return CRUDHelper.findById(FIND_PRODUCT_DISCOUNT_TYPE, id, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<ProductDiscountType> getAll(int limit, int offset) throws RepositoryException {
        try {
            return CRUDHelper.getAll(GET_PRODUCT_DISCOUNT_TYPES, discountWorker, limit, offset);
        } catch (SQLException | ConnectionPoolException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public boolean update(ProductDiscountType discountCard) throws RepositoryException {
        try {
            return CRUDHelper.update(discountCard, UPDATE_PRODUCT_DISCOUNT_TYPE, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        try {
            return CRUDHelper.delete(DELETE_PRODUCT_DISCOUNT_TYPE, id);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }
}
