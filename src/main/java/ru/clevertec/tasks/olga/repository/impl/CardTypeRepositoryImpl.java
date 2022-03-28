package ru.clevertec.tasks.olga.repository.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.repository.CardTypeRepository;
import ru.clevertec.tasks.olga.repository.common.DbHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import ru.clevertec.tasks.olga.util.orm.WorkerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@NoArgsConstructor
@Slf4j
public class CardTypeRepositoryImpl implements CardTypeRepository {

    private static final NodeWorker<CardType> discountWorker =
            WorkerFactory.getInstance().getDiscountTypeWorker();

    @Override
    public long save(CardType discountCard) {
        try {
            return DbHelper.save(discountCard, INSERT_DISCOUNT_TYPE, discountWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public Optional<CardType> findById(long id) {
        try {
            return DbHelper.findById(FIND_DISCOUNT_TYPE, id, discountWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public List<CardType> getAll(int limit, int offset) {
        try {
            return DbHelper.getAll(GET_DISCOUNT_TYPES, discountWorker, limit, offset);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public boolean update(CardType discountCard) {
        try {
            return DbHelper.update(discountCard, UPDATE_DISCOUNT_TYPE, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            return DbHelper.delete(DELETE_DISCOUNT_TYPE, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }
}
