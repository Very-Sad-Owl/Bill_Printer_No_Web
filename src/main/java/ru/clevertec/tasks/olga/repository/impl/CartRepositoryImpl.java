package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.CartRepository;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Repository
@Slf4j
public class CartRepositoryImpl implements CartRepository {

    private final ModelRowMapper<Cart> cartWorker;
    private final ModelRowMapper<Slot> slotWorker;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplateObject;

    @Autowired
    public CartRepositoryImpl(ModelRowMapper<Cart> cartWorker, ModelRowMapper<Slot> slotWorker, DataSource dataSource, JdbcTemplate jdbcTemplateObject) {
        this.cartWorker = cartWorker;
        this.slotWorker = slotWorker;
        this.dataSource = dataSource;
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public long save(Cart cart) throws RepositoryException {
        return 0;
    }

    @Override
    public Optional<Cart> findById(long id) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public List<Cart> getAll(int limit, int offset) throws RepositoryException {
        return null;
    }

    @Override
    public boolean update(Cart cart) throws RepositoryException {
        return false;
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        return false;
    }
}
