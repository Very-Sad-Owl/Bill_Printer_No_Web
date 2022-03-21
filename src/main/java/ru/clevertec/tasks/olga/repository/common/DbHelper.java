package ru.clevertec.tasks.olga.repository.common;

import ru.clevertec.custom_collection.my_list.ArrayListImpl;
import ru.clevertec.tasks.olga.exception.ReadingException;
import ru.clevertec.tasks.olga.exception.WritingException;
import ru.clevertec.tasks.olga.model.AbstractModel;
import ru.clevertec.tasks.olga.repository.connection.ConnectionPool;
import ru.clevertec.tasks.olga.repository.connection.ConnectionProvider;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;
import ru.clevertec.tasks.olga.util.orm.NodeWorker;
import ru.clevertec.tasks.olga.util.orm.WorkerFactory;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DbHelper {

    public static <T extends AbstractModel> long save(T model, String query, NodeWorker<T> worker)
            throws ConnectionPoolException, SQLException {
        PreparedStatement st = null;
        ConnectionPool pool = null;
        Connection con = null;

        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            worker.modelToNode(model, st);
            return st.executeUpdate();
//            return getGeneratedKey(st);
        } finally {
            if (pool != null) {
                pool.closeConnection(con, st);
            }
        }
    }

    public static <T extends AbstractModel> PreparedStatement save(T model, String query, NodeWorker<T> worker,
                                                                   Connection con) throws SQLException {
        PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        worker.modelToNode(model, st);
        st.executeUpdate();
        return st;
    }

    public static <T extends AbstractModel> List<T> getAll(String query, NodeWorker<T> worker)
            throws ConnectionPoolException, SQLException {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection con = null;
        ResultSet rs = null;
        List<T> found = new ArrayListImpl<>();
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                found.add(worker.nodeToModel(rs, false));
            }
        } finally {
            if (pool != null) {
                pool.closeConnection(con, ps, rs);
            }
        }
        return found;
    }

    public static <T extends AbstractModel> List<T> getAll(String query, NodeWorker<T> worker,
                                                           Connection con, PreparedStatement ps, ResultSet rs)
            throws SQLException {
        List<T> found = new ArrayListImpl<>();
        ps = con.prepareStatement(query);
        rs = ps.executeQuery();
        while (rs.next()) {
            found.add(worker.nodeToModel(rs, false));
        }
        return found;
    }

    public static <T extends AbstractModel> Optional<T> findById(String query, long id, NodeWorker<T> worker)
            throws ConnectionPoolException, SQLException {
        PreparedStatement ps = null;
        ConnectionPool pool = null;
        Connection con = null;
        ResultSet rs = null;
        Optional<T> found = null;
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            found = findById(query, id, worker, con, ps, rs);
            return found;
        } finally {
            if (pool != null) {
                pool.closeConnection(con, ps, rs);
            }
        }
    }

    public static <T extends AbstractModel> Optional<T> findById(String query, long id, NodeWorker<T> worker,
                                                                 Connection con, PreparedStatement ps, ResultSet rs)
            throws SQLException {
        T found = null;
        ps = con.prepareStatement(query);
        ps.setLong(1, id);
        rs = ps.executeQuery();
        if (rs.next()) {
            found = worker.nodeToModel(rs, false);
        }
        return Optional.of(found);

    }

    public static <T extends AbstractModel> List<T> findAllById(String query, long id, NodeWorker<T> worker,
                                                                 Connection con, PreparedStatement ps, ResultSet rs)
            throws SQLException {
        List<T> found = new ArrayListImpl<>();
        ps = con.prepareStatement(query);
        ps.setLong(1, id);
        rs = ps.executeQuery();
        if (rs.next()) {
            found.add(worker.nodeToModel(rs, false));
        }
        return found;

    }

    public static <T extends AbstractModel> boolean update(T model, String query, NodeWorker<T> worker)
            throws SQLException, ConnectionPoolException {
        ConnectionPool pool = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            ps = con.prepareStatement(query);
            worker.modelToNode(model, ps);
            ps.setLong(4, model.getId());
            ps.executeUpdate();
        } finally {
            if (pool != null) {
                pool.closeConnection(con, ps);
            }
        }
        return true;
    }

    public static <T extends AbstractModel> PreparedStatement update(T model, String query, NodeWorker<T> worker, Connection con)
            throws SQLException {
        PreparedStatement ps;
        ps = con.prepareStatement(query);
        worker.modelToNode(model, ps);
        ps.setLong(4, model.getId());
        ps.executeUpdate();
        return ps;
    }

    public static boolean delete(String query, long id)
            throws SQLException, ConnectionPoolException {
        ConnectionPool pool = null;
        Connection con = null;
        PreparedStatement ps = null;
        int res;

        try {
            pool = ConnectionProvider.getConnectionPool();
            con = pool.takeConnection();
            ps = con.prepareStatement(query);
            ps.setLong(1, id);

            res = ps.executeUpdate();
        } finally {
            if (pool != null) {
                pool.closeConnection(con, ps);
            }
        }

        return res != 0;
    }

    public static long getGeneratedKey(PreparedStatement st) throws SQLException {
        ResultSet generatedKeys = st.getGeneratedKeys();
        if ( generatedKeys.next() ) {
            return generatedKeys.getLong(1);
        } else{
            throw new SQLException();
        }
    }
}