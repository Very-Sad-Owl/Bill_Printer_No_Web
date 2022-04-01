package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.repository.ProductDiscountRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;
import ru.clevertec.tasks.olga.util.tablemapper.WorkerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;


@Slf4j
public class ProductDiscountRepositoryImpl implements ProductDiscountRepository {

    private static final NodeWorker<ProductDiscountType> discountWorker =
            WorkerFactory.getInstance().getProductDiscountWorker();

    @Override
    public long save(ProductDiscountType discountCard) {
        try {
            return CRUDHelper.save(discountCard, INSERT_PRODUCT_DISCOUNT_TYPE, discountWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public Optional<ProductDiscountType> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_PRODUCT_DISCOUNT_TYPE, id, discountWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public List<ProductDiscountType> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_PRODUCT_DISCOUNT_TYPES, discountWorker, limit, offset);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public boolean update(ProductDiscountType discountCard) {
        try {
            return CRUDHelper.update(discountCard, UPDATE_PRODUCT_DISCOUNT_TYPE, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            return CRUDHelper.delete(DELETE_PRODUCT_DISCOUNT_TYPE, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }
}
