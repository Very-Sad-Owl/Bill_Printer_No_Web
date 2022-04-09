package ru.clevertec.tasks.olga.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.entity.CardType;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.CardTypeRepository;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Repository //TODO: DataAccessException
public class CardTypeRepositoryImpl implements CardTypeRepository {

    private final ModelRowMapper<CardType> discountWorker;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public CardTypeRepositoryImpl(ModelRowMapper<CardType> discountWorker, NamedParameterJdbcTemplate template) {
        this.discountWorker = discountWorker;
        this.template = template;
    }

    @Override
    public long save(CardType cardType) throws RepositoryException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", cardType.getTitle());
        params.addValue("discount", cardType.getDiscount());
        params.addValue("id", cardType.getId());
        template.update(INSERT_DISCOUNT_TYPE, params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<CardType> findById(long id) throws RepositoryException {
        return Optional.ofNullable(template.queryForObject(FIND_DISCOUNT_TYPE,
                new MapSqlParameterSource("id", id), discountWorker));
    }

    @Override
    public List<CardType> getAll(Pageable pageable) throws RepositoryException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("page_limit", pageable.getPageSize());
        params.addValue("page", pageable.getPageNumber());
        return template.query(GET_DISCOUNT_TYPES, params, discountWorker);
    }

    @Override
    public boolean update(CardType cardType) throws RepositoryException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", cardType.getTitle());
        params.addValue("discount", cardType.getDiscount());
        params.addValue("id", cardType.getId());
        return template.update(UPDATE_DISCOUNT_TYPE, params) != 0;
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        return template.update(DELETE_DISCOUNT_TYPE, new MapSqlParameterSource("id", id)) != 0;
    }
}
