package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.tasks.olga.entity.Cart;
import ru.clevertec.tasks.olga.entity.Slot;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.BillRepository;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Repository
@Slf4j
public class BillRepositoryImpl implements BillRepository {

    private final ModelRowMapper<Cart> cartWorker;
    private final ModelRowMapper<Slot> slotWorker;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public BillRepositoryImpl(ModelRowMapper<Cart> cartWorker, ModelRowMapper<Slot> slotWorker, NamedParameterJdbcTemplate template) {
        this.cartWorker = cartWorker;
        this.slotWorker = slotWorker;
        this.template = template;
    }

    @Transactional
    @Override
    public long save(Cart cart) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource billParams = new MapSqlParameterSource();
        billParams.addValue("card", cart.getDiscountCard().getId());
        billParams.addValue("cashier", cart.getCashier().getId());
        billParams.addValue("price", cart.getPrice());
        template.update(INSERT_CART, billParams, keyHolder, new String[]{"bill_id"});
        long generatedKey = keyHolder.getKey().longValue();

        for (Slot slot : cart.getPositions()) {
            MapSqlParameterSource slotParams = new MapSqlParameterSource();
            slotParams.addValue("product", slot.getProduct().getId());
            slotParams.addValue("quantity", slot.getQuantity());
            slotParams.addValue("price", slot.getTotalPrice());
            slotParams.addValue("cart", generatedKey);
            if (template.update(INSERT_SLOT, slotParams) == 0) return -1;
        }

        return generatedKey;
    }

    @Override
    public Optional<Cart> findById(long id) {
        List<Slot> slots = template.query(FIND_SLOTS_BY_CART_ID,
                new MapSqlParameterSource("cart", id), slotWorker);
        Optional<Cart> cart = Optional.ofNullable(template.queryForObject(FIND_CART_BY_ID,
                new MapSqlParameterSource("id", id), cartWorker));
        cart.ifPresent(value -> value.setPositions(slots));
        return cart;
    }

    @Override
    public List<Cart> getAll(Pageable pageable) {
        pageable = pageable.previousOrFirst();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("page_limit", pageable.getPageSize());
        params.addValue("page", pageable.getOffset());
        List<Cart> bills = template.query(GET_CARTS, params, cartWorker);
        for (Cart bill : bills) {
            List<Slot> slots = template.query(FIND_SLOTS_BY_CART_ID,
                    new MapSqlParameterSource("cart", bill.getId()), slotWorker);
            bill.setPositions(slots);
        }
        return bills;
    }

    @Transactional
    @Override
    public boolean update(Cart cart) {
        MapSqlParameterSource billParams = new MapSqlParameterSource();
        billParams.addValue("card", cart.getDiscountCard().getId());
        billParams.addValue("cashier", cart.getCashier().getId());
        billParams.addValue("price", cart.getPrice());
        billParams.addValue("id", cart.getId());
        if (template.update(UPDATE_CART, billParams) == 0) return false;

        for (Slot slot : cart.getPositions()) {
            MapSqlParameterSource slotParams = new MapSqlParameterSource();
            slotParams.addValue("product", slot.getProduct().getId());
            slotParams.addValue("quantity", slot.getQuantity());
            slotParams.addValue("price", slot.getTotalPrice());
            slotParams.addValue("cart", cart.getId());
            if (template.update(INSERT_SLOT, slotParams) == 0) return false;
        }
        return true;
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        if (template.update(DELETE_CART, new MapSqlParameterSource("id", id)) == 0) {
            return false;
        }
        template.update(DELETE_SLOTS, new MapSqlParameterSource());
        return true;
    }
}
