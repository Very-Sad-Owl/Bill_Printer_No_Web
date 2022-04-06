package ru.clevertec.tasks.olga.repository.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.annotation.UseCache;
import ru.clevertec.tasks.olga.exception.repoexc.ConnectionException;
import ru.clevertec.tasks.olga.exception.repoexc.ReadingException;
import ru.clevertec.tasks.olga.exception.repoexc.RepositoryException;
import ru.clevertec.tasks.olga.exception.repoexc.WritingException;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.repository.CardTypeRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Repository
public class CardTypeRepositoryImpl implements CardTypeRepository {

    private final NodeWorker<CardType> discountWorker;

    @Autowired
    public CardTypeRepositoryImpl(NodeWorker<CardType> discountWorker) {
        this.discountWorker = discountWorker;
    }

    @Override
    @UseCache
    @SneakyThrows
    public long save(CardType discountCard) {
        try {
            return CRUDHelper.save(discountCard, INSERT_DISCOUNT_TYPE, discountWorker);
        } catch (SQLException e) {
            throw new WritingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public Optional<CardType> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_DISCOUNT_TYPE, id, discountWorker);
        } catch (SQLException e) {
            throw new ReadingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @SneakyThrows
    public List<CardType> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_DISCOUNT_TYPES, discountWorker, limit, offset);
        } catch (SQLException e) {
            throw new ReadingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }

    @Override
    @UseCache
    @SneakyThrows
    public boolean update(CardType discountCard) {
        try {
            return CRUDHelper.update(discountCard, UPDATE_DISCOUNT_TYPE, discountWorker);
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
            return CRUDHelper.delete(DELETE_DISCOUNT_TYPE, id);
        } catch (SQLException e) {
            throw new WritingException();
        } catch (ConnectionPoolException e){
            throw new ConnectionException();
        }
    }
}
