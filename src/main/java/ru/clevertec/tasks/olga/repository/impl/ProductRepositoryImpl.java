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
import ru.clevertec.tasks.olga.annotation.UseCache;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ModelRowMapper<Product> productWorker;
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public ProductRepositoryImpl(ModelRowMapper<Product> productWorker, NamedParameterJdbcTemplate template) {
        this.productWorker = productWorker;
        this.template = template;
    }

    @Override
    @UseCache
    public long save(Product product) throws RepositoryException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("title", product.getTitle());
            params.addValue("price", product.getPrice());
            params.addValue("disc_id", product.getDiscountType().getId());
            template.update(INSERT_PRODUCT, params, keyHolder, new String[]{"product_id"});
            return keyHolder.getKey().longValue();
        } catch (DataAccessException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    @UseCache
    public Optional<Product> findById(long id) throws RepositoryException {
        try {
            return Optional.ofNullable(template.queryForObject(FIND_PRODUCT_BY_ID,
                    new MapSqlParameterSource("id", id), productWorker));
        } catch (DataAccessException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    public List<Product> getAll(Pageable pageable) throws RepositoryException {
        try {
            pageable = pageable.previousOrFirst();
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("page_limit", pageable.getPageSize());
            params.addValue("page", pageable.getPageNumber());
            return template.query(GET_PRODUCTS, params, productWorker);
        } catch (DataAccessException e) {
            throw new ReadingException(e.getMessage());
        }
    }

    @Override
    @UseCache
    public boolean update(Product product) throws RepositoryException {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("title", product.getTitle());
            params.addValue("price", product.getPrice());
            params.addValue("disc_id", product.getDiscountType().getId());
            params.addValue("id", product.getId());
            return template.update(UPDATE_PRODUCT, params) != 0;
        } catch (DataAccessException e) {
            throw new WritingException(e.getMessage());
        }
    }

    @Override
    @UseCache
    public boolean delete(long id) throws RepositoryException {
        try {
            return template.update(DELETE_PRODUCT, new MapSqlParameterSource("id", id)) != 0;
        } catch (DataAccessException e) {
            throw new WritingException(e.getMessage());
        }
    }
}
