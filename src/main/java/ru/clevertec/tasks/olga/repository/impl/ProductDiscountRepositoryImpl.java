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
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.repository.ProductDiscountRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;


@Slf4j
@Repository
public class ProductDiscountRepositoryImpl implements ProductDiscountRepository {

    private final NodeWorker<ProductDiscountType> discountWorker;

    @Autowired
    public ProductDiscountRepositoryImpl(NodeWorker<ProductDiscountType> discountWorker) {
        this.discountWorker = discountWorker;
    }

    @Override
    @UseCache
    @SneakyThrows
    public long save(ProductDiscountType discountCard) {
        try {
            return CRUDHelper.save(discountCard, INSERT_PRODUCT_DISCOUNT_TYPE, discountWorker);
        } catch (SQLException e) {
            throw new WritingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public Optional<ProductDiscountType> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_PRODUCT_DISCOUNT_TYPE, id, discountWorker);
        } catch (SQLException e) {
            throw new ReadingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @SneakyThrows
    public List<ProductDiscountType> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_PRODUCT_DISCOUNT_TYPES, discountWorker, limit, offset);
        } catch (SQLException e) {
            throw new ReadingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public boolean update(ProductDiscountType discountCard) {
        try {
            return CRUDHelper.update(discountCard, UPDATE_PRODUCT_DISCOUNT_TYPE, discountWorker);
        } catch (SQLException e) {
            throw new WritingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public boolean delete(long id) {
        try {
            return CRUDHelper.delete(DELETE_PRODUCT_DISCOUNT_TYPE, id);
        } catch (SQLException e) {
            throw new WritingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }
}
