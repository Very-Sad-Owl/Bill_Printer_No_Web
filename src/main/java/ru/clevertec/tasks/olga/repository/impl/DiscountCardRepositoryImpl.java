package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.repository.DiscountCardRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;
import ru.clevertec.tasks.olga.util.tablemapper.WorkerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;


@Slf4j
public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private static final NodeWorker<DiscountCard> discountWorker = WorkerFactory.getInstance().getDiscountWorker();

    @Override
    public long save(DiscountCard discountCard) {
        try {
            return CRUDHelper.save(discountCard, INSERT_DISCOUNT, discountWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public Optional<DiscountCard> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_DISCOUNT_BY_ID, id, discountWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public List<DiscountCard> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_DISCOUNTS, discountWorker, limit, offset);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public boolean update(DiscountCard discountCard) {
        try {
            return CRUDHelper.update(discountCard, UPDATE_DISCOUNT, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            return CRUDHelper.delete(DELETE_DISCOUNT, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }
}
