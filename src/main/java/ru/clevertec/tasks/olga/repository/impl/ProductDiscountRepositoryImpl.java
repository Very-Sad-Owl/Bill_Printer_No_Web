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
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.ProductDiscountRepository;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;


@Slf4j
@Repository
public class ProductDiscountRepositoryImpl implements ProductDiscountRepository {

    private final ModelRowMapper<ProductDiscountType> discountWorker;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public ProductDiscountRepositoryImpl(ModelRowMapper<ProductDiscountType> discountWorker, NamedParameterJdbcTemplate template) {
        this.discountWorker = discountWorker;
        this.template = template;
    }

    @Override
    public long save(ProductDiscountType productDiscountType) throws RepositoryException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", productDiscountType.getTitle());
        params.addValue("val", productDiscountType.getDiscount());
        params.addValue("quantity", productDiscountType.getRequiredMinQuantity());
        template.update(INSERT_PRODUCT_DISCOUNT_TYPE, params, keyHolder, new String[]{"discount_id"});
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<ProductDiscountType> findById(long id) throws RepositoryException {
        return Optional.ofNullable(template.queryForObject(FIND_PRODUCT_DISCOUNT_TYPE,
                new MapSqlParameterSource("id", id), discountWorker));
    }

    @Override
    public List<ProductDiscountType> getAll(Pageable pageable) throws RepositoryException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("page_limit", pageable.getPageSize());
        params.addValue("page", pageable.getPageNumber());
        return template.query(GET_PRODUCT_DISCOUNT_TYPES, params, discountWorker);
    }

    @Override
    public boolean update(ProductDiscountType productDiscountType) throws RepositoryException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", productDiscountType.getTitle());
        params.addValue("val", productDiscountType.getDiscount());
        params.addValue("quantity", productDiscountType.getRequiredMinQuantity());
        params.addValue("id", productDiscountType.getId());
        return template.update(UPDATE_PRODUCT_DISCOUNT_TYPE, params) != 0;
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        return template.update(DELETE_PRODUCT_DISCOUNT_TYPE, new MapSqlParameterSource("id", id)) != 0;
    }
}
