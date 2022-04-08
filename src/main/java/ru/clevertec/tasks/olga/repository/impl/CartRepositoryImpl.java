package ru.clevertec.tasks.olga.repository.impl;

import com.google.common.base.Defaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.CartRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.repository.connection.ConnectionPool;
import ru.clevertec.tasks.olga.repository.connection.ConnectionProvider;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.tablemapper.NodeWorker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Repository
@Slf4j
public class CartRepositoryImpl implements CartRepository {

    private final NodeWorker<Cart> cartWorker;
    private final NodeWorker<Slot> slotWorker;

    @Autowired
    public CartRepositoryImpl(NodeWorker<Cart> cartWorker, NodeWorker<Slot> slotWorker) {
        this.cartWorker = cartWorker;
        this.slotWorker = slotWorker;
    }

    @Override
    public long save(Cart cart) throws RepositoryException {
        PreparedStatement st = null;
        PreparedStatement slotSt;
        ConnectionPool pool = null;
        Connection con = null;
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            con.setAutoCommit(false);
            st = CRUDHelper.save(cart, INSERT_CART, cartWorker, con);
            long insertedId = CRUDHelper.getGeneratedKey(st);
            for (Slot el : cart.getPositions()) {
                slotSt = CRUDHelper.save(el, INSERT_SLOT, slotWorker, con);
                long insertedSlotId = CRUDHelper.getGeneratedKey(slotSt);
                setSlotCartId(insertedSlotId, insertedId, con, slotSt);
            }
            con.commit();
            return insertedId;
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        } finally {
            if (pool != null) {
                pool.closeConnection(con, st);
            }
        }
    }

    public Optional<Cart> findById(long id) throws RepositoryException {
        Optional<Cart> cart;
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            con.setAutoCommit(false);
            cart = CRUDHelper.findById(FIND_CART_BY_ID, id, cartWorker, con, ps, rs);
            rs = findSlotsByCartId(id, con, ps);
            List<Slot> slots = new ArrayListImpl<>();
            while (rs.next()){
                slots.add(slotWorker.nodeToModel(rs, false));
            }
            if (cart.isPresent()) cart.get().setPositions(slots);
            con.commit();
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        } finally {
            if (pool != null) {
                pool.closeConnection(con, ps, rs);
            }
        }
        return cart;
    }

    @Override
    public List<Cart> getAll(int limit, int offset) throws RepositoryException {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection con = null;
        ResultSet rs = null;
        List<Cart> bills;
        List<Slot> slots;
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            con.setAutoCommit(false);
            bills = CRUDHelper.getAll(GET_CARTS, cartWorker, con, ps, rs, limit, offset);
            for (Cart cart : bills){
                slots = CRUDHelper.findAllById(FIND_SLOTS_BY_CART_ID, cart.getId(), slotWorker, con, ps, rs);
                cart.setPositions(slots);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new ReadingException(e.getMessage());
        } finally {
            if (pool != null) {
                pool.closeConnection(con, ps, rs);
            }
        }
        return bills;
    }

    @Override
    public boolean update(Cart cart) throws RepositoryException {
        PreparedStatement st = null;
        ConnectionPool pool = null;
        Connection con = null;
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            con.setAutoCommit(false);
            st = CRUDHelper.prepareToUpdate(cart, UPDATE_CART, cartWorker, con);
            if (!CRUDHelper.update(cart, cartWorker, st)) return false;
            for (Slot el : cart.getPositions()){
                if (el.getId() == Defaults.defaultValue(Long.TYPE)){
                    st = CRUDHelper.save(el, INSERT_SLOT, slotWorker, con);
                } else {
                    st = CRUDHelper.prepareToUpdate(el, UPDATE_SLOT, slotWorker, con);
                    if (!CRUDHelper.update(el, slotWorker, st)) return false;
                }
            }
            con.commit();
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        } finally {
            if (pool != null) {
                pool.closeConnection(con, st);
            }
        }
        return true;
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        try {
            return CRUDHelper.delete(DELETE_CART, id);
        } catch (SQLException | ConnectionPoolException e) {
            throw new WritingException(e.getMessage());
        }
    }

    private void setSlotCartId(long slotId, long cartId, Connection con, PreparedStatement ps) throws SQLException {
        ps = con.prepareStatement(SET_SLOT_CART_ID);
        ps.setLong(1, cartId);
        ps.setLong(2, slotId);
        ps.executeUpdate();
    }

    private ResultSet findSlotsByCartId(long cartId, Connection con, PreparedStatement ps) throws SQLException {
        ps = con.prepareStatement(FIND_SLOTS_BY_CART_ID);
        ps.setLong(1, cartId);
        return ps.executeQuery();
    }

}
