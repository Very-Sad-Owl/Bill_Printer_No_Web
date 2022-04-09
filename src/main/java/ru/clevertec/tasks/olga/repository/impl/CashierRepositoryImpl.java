package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.CashierRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
@Repository
public class CashierRepositoryImpl implements CashierRepository {

    private final ModelRowMapper<Cashier> cashierWorker;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public CashierRepositoryImpl(ModelRowMapper<Cashier> cashierWorker, NamedParameterJdbcTemplate template) {
        this.cashierWorker = cashierWorker;
        this.template = template;
    }

    @Override
    public long save(Cashier cashier) throws RepositoryException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", cashier.getName());
        params.addValue("surname", cashier.getSurname());
        params.addValue("id", cashier.getId());
        template.update(INSERT_CASHIER, params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Cashier> findById(long id) throws RepositoryException {
        return Optional.ofNullable(template.queryForObject(FIND_CASHIER_BY_ID,
                new MapSqlParameterSource("id", id), cashierWorker));
    }

    @Override
    public List<Cashier> getAll(Pageable pageable) throws RepositoryException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("page_limit", pageable.getPageSize());
        params.addValue("page", pageable.getPageNumber());
        return template.query(GET_CASHIERS, params, cashierWorker);
    }

    @Override
    public boolean update(Cashier cashier) throws RepositoryException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", cashier.getName());
        params.addValue("surname", cashier.getSurname());
        params.addValue("id", cashier.getId());
        return template.update(UPDATE_CASHIER, params) != 0;
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        return template.update(DELETE_CASHIER, new MapSqlParameterSource("id", id)) != 0;
    }
}
