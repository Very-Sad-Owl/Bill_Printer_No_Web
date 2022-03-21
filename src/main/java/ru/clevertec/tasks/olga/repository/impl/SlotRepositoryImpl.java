package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.model.Slot;
import ru.clevertec.tasks.olga.repository.SlotRepository;
import ru.clevertec.tasks.olga.repository.common.DbHelper;
import ru.clevertec.tasks.olga.repository.connection.ConnectionPool;
import ru.clevertec.tasks.olga.repository.connection.ConnectionProvider;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import ru.clevertec.tasks.olga.util.orm.WorkerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
public class SlotRepositoryImpl implements SlotRepository {

    private static final NodeWorker<Slot> slotWorker = WorkerFactory.getInstance().getSlotWorker();

    @Override
    public long save(Slot slot) {
        try {
            return DbHelper.save(slot, INSERT_SLOT, slotWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public Optional<Slot> findById(long id) {
        try {
            return DbHelper.findById(FIND_SLOT_BY_ID, id, slotWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public List<Slot> getAll() {
        try {
            return DbHelper.getAll(GET_SLOTS, slotWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.connection");
        }
    }

    @Override
    public boolean update(Slot slot) {
        try {
            return DbHelper.update(slot, UPDATE_SLOT, slotWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            return DbHelper.delete(DELETE_SLOT, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

}
