package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.model.Cart;
import ru.clevertec.tasks.olga.model.Slot;
import ru.clevertec.tasks.olga.model.dto.CartDto;
import ru.clevertec.tasks.olga.model.dto.SlotDto;
import ru.clevertec.tasks.olga.repository.CartRepository;
import lombok.AllArgsConstructor;
import ru.clevertec.tasks.olga.repository.SlotRepository;
import ru.clevertec.tasks.olga.repository.common.DbHelper;
import ru.clevertec.tasks.olga.repository.connection.ConnectionPool;
import ru.clevertec.tasks.olga.repository.connection.ConnectionProvider;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.repository.factory.RepositoryFactory;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import ru.clevertec.tasks.olga.util.orm.WorkerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@AllArgsConstructor
@Slf4j
public class CartRepositoryImpl implements CartRepository {

    private static final NodeWorker<Cart> cartWorker = WorkerFactory.getInstance().getCartWorker();
    private static final NodeWorker<Slot> slotWorker = WorkerFactory.getInstance().getSlotWorker();

    @Override
    public long save(Cart cart) {
        PreparedStatement st = null;
        PreparedStatement slotSt = null;
        ConnectionPool pool = null;
        Connection con = null;
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            con.setAutoCommit(false);
            st = DbHelper.save(cart, INSERT_CART, cartWorker, con);
            for (Slot el : cart.getPositions()){
                slotSt = DbHelper.save(el, INSERT_SLOT, slotWorker, con);
                setSlotCartId(el.getId(), cart.getId(), con, slotSt);
            }
            con.commit();
            return DbHelper.getGeneratedKey(st);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        } finally {
            if (pool != null) {
                pool.closeConnection(con, st);
            }
        }
            }

    @Override
    public Optional<Cart> findById(long id) {
        Optional<Cart> cart;
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            con.setAutoCommit(false);
            cart = DbHelper.findById(FIND_CART_BY_ID, id, cartWorker, con, ps, rs);
            List<Slot> slots = DbHelper.getAll(GET_SLOTS, slotWorker, con, ps, rs);
            if (cart.isPresent()) cart.get().setPositions(slots);
            con.commit();
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.reading");
        } finally {
            if (pool != null) {
                pool.closeConnection(con, ps, rs);
            }
        }
        return cart;
    }

    @Override
    public List<Cart> getAll() {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection con = null;
        ResultSet rs = null;
        List<Cart> bills = new ArrayListImpl<>();
        List<Slot> slots = new ArrayListImpl<>();
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            con.setAutoCommit(false);
            bills = DbHelper.getAll(GET_CARTS, cartWorker, con, ps, rs);
            for (Cart cart : bills){
                slots = DbHelper.findAllById(FIND_SLOTS_BY_CART_ID, cart.getId(), slotWorker, con, ps, rs);
                cart.setPositions(slots);
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.error(e.getMessage());
            throw new ReadingException("error.connection");
        } finally {
            if (pool != null) {
                pool.closeConnection(con, ps, rs);
            }
        }
        return bills;
    }

    @Override
    public boolean update(Cart cart) {
        PreparedStatement st = null;
        ConnectionPool pool = null;
        Connection con = null;
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            con.setAutoCommit(false);
            st = DbHelper.update(cart, UPDATE_CART, cartWorker, con);
            for (Slot el : cart.getPositions()){
                st = DbHelper.update(el, UPDATE_SLOT, slotWorker, con);
            }
            con.commit();
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        } finally {
            if (pool != null) {
                pool.closeConnection(con, st);
            }
        }
        return true;
    }

    @Override
    public boolean delete(long id) {
        try {
            return DbHelper.delete(DELETE_CART, id);
        } catch (SQLException | ConnectionPoolException e) {
            log.error(e.getMessage());
            throw new WritingException("error.writing");
        }
    }

    private void setSlotCartId(long slotId, long catId, Connection con, PreparedStatement ps) throws SQLException {
        ps = con.prepareStatement(SET_SLOT_CART_ID);
        ps.setLong(1, catId);
        ps.setLong(2, slotId);
        ps.executeUpdate();
    }

}
