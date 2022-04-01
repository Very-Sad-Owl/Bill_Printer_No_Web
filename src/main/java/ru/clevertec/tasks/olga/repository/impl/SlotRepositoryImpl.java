package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.repository.SlotRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;
import ru.clevertec.tasks.olga.util.tablemapper.WorkerFactory;

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
            return CRUDHelper.save(slot, INSERT_SLOT, slotWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public Optional<Slot> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_SLOT_BY_ID, id, slotWorker);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        }
    }

    @Override
    public List<Slot> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_SLOTS, slotWorker, limit, offset);
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.connection");
        }
    }

    @Override
    public boolean update(Slot slot) {
        try {
            return CRUDHelper.update(slot, UPDATE_SLOT, slotWorker);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            return CRUDHelper.delete(DELETE_SLOT, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

}
