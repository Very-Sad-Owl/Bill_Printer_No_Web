package ru.clevertec.tasks.olga.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.CardTypeRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Repository
public class CardTypeRepositoryImpl implements CardTypeRepository {

    private final ModelRowMapper<CardType> discountWorker;

    @Autowired
    public CardTypeRepositoryImpl(ModelRowMapper<CardType> discountWorker) {
        this.discountWorker = discountWorker;
    }

    @Override
    public long save(CardType discountCard) throws RepositoryException {
        try {
            return CRUDHelper.save(discountCard, INSERT_DISCOUNT_TYPE, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    public Optional<CardType> findById(long id) throws RepositoryException {
        try {
            return CRUDHelper.findById(FIND_DISCOUNT_TYPE, id, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    public List<CardType> getAll(int limit, int offset) throws RepositoryException {
        try {
            return CRUDHelper.getAll(GET_DISCOUNT_TYPES, discountWorker, limit, offset);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    public boolean update(CardType discountCard) throws RepositoryException {
        try {
            return CRUDHelper.update(discountCard, UPDATE_DISCOUNT_TYPE, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        try {
            return CRUDHelper.delete(DELETE_DISCOUNT_TYPE, id);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }
}
