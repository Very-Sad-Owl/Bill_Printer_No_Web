package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.entity.Cashier;
import ru.clevertec.tasks.olga.entity.DiscountCard;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.DiscountCardRepository;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;


@Slf4j
@Repository
public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private final ModelRowMapper<DiscountCard> discountWorker;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public DiscountCardRepositoryImpl(ModelRowMapper<DiscountCard> discountWorker, NamedParameterJdbcTemplate template) {
        this.discountWorker = discountWorker;
        this.template = template;
    }

    @Override
    public long save(DiscountCard discountCard) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bday", discountCard.getBirthday());
        params.addValue("disc_id", discountCard.getCardType().getId());
        template.update(INSERT_DISCOUNT, params, keyHolder, new String[]{"card_id"});
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<DiscountCard> findById(long id) {
        return Optional.ofNullable(template.queryForObject(FIND_DISCOUNT_BY_ID,
                new MapSqlParameterSource("id", id), discountWorker));
    }

    @Override
    public List<DiscountCard> getAll(Pageable pageable) {
        pageable = pageable.previousOrFirst();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("page_limit", pageable.getPageSize());
        params.addValue("page", pageable.getPageNumber());
        return template.query(GET_DISCOUNTS, params, discountWorker);
    }

    @Override
    public boolean update(DiscountCard discountCard) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bday", discountCard.getBirthday());
        params.addValue("disc_id", discountCard.getCardType().getId());
        params.addValue("id", discountCard.getId());
        return template.update(UPDATE_DISCOUNT, params) != 0;
    }

    @Override
    public boolean delete(long id) {
        return template.update(DELETE_DISCOUNT, new MapSqlParameterSource("id", id)) != 0;
    }

}
