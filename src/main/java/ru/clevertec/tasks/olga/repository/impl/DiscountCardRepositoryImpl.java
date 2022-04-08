package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.DiscountCardRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;


@Slf4j
@Repository
public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private final ModelRowMapper<DiscountCard> discountWorker;

    @Autowired
    public DiscountCardRepositoryImpl(ModelRowMapper<DiscountCard> discountWorker) {
        this.discountWorker = discountWorker;
    }

    @Override
    public long save(DiscountCard discountCard) throws RepositoryException {
        try {
            return CRUDHelper.save(discountCard, INSERT_DISCOUNT, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    public Optional<DiscountCard> findById(long id) throws RepositoryException {
        try {
            return CRUDHelper.findById(FIND_DISCOUNT_BY_ID, id, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    public List<DiscountCard> getAll(int limit, int offset) throws RepositoryException {
        try {
            return CRUDHelper.getAll(GET_DISCOUNTS, discountWorker, limit, offset);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    public boolean update(DiscountCard discountCard) throws RepositoryException {
        try {
            return CRUDHelper.update(discountCard, UPDATE_DISCOUNT, discountWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        try {
            return CRUDHelper.delete(DELETE_DISCOUNT, id);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }
}
