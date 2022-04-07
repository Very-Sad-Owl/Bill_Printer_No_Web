package ru.clevertec.tasks.olga.repository.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.annotation.UseCache;
import ru.clevertec.tasks.olga.exception.handeled.ReadingException;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.exception.handeled.DeletionExceptionHandled;
import ru.clevertec.tasks.olga.exception.handeled.SavingExceptionHandled;
import ru.clevertec.tasks.olga.exception.handeled.UpdatingExceptionHandled;
import ru.clevertec.tasks.olga.repository.SlotRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
@Repository
public class SlotRepositoryImpl implements SlotRepository {

    private final NodeWorker<Slot> slotWorker;
    @Autowired
    public SlotRepositoryImpl(NodeWorker<Slot> slotWorker) {
        this.slotWorker = slotWorker;
    }

    @Override
    @SneakyThrows
    public long save(Slot slot) {
        try {
            return CRUDHelper.save(slot, INSERT_SLOT, slotWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new SavingExceptionHandled(e);
        }
    }

    @Override
    @SneakyThrows
    public Optional<Slot> findById(long id) {
        try {
            return CRUDHelper.findById(FIND_SLOT_BY_ID, id, slotWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e);
        }
    }

    @Override
    @SneakyThrows
    public List<Slot> getAll(int limit, int offset) {
        try {
            return CRUDHelper.getAll(GET_SLOTS, slotWorker, limit, offset);
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e);
        }
    }

    @Override
    @SneakyThrows
    public boolean update(Slot slot) {
        try {
            return CRUDHelper.update(slot, UPDATE_SLOT, slotWorker);
        } catch (SQLException | ConnectionPoolException e) {
            throw new UpdatingExceptionHandled(e);
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(long id) {
        try {
            return CRUDHelper.delete(DELETE_SLOT, id);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DeletionExceptionHandled(e);
        }
    }

}
