package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.entity.ProductDiscountType;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.ProductDiscountRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;


@Slf4j
@Repository
public class ProductDiscountRepositoryImpl implements ProductDiscountRepository {

    private final ModelRowMapper<ProductDiscountType> discountWorker;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplateObject;

    @Autowired
    public ProductDiscountRepositoryImpl(ModelRowMapper<ProductDiscountType> discountWorker, DataSource dataSource, JdbcTemplate jdbcTemplateObject) {
        this.discountWorker = discountWorker;
        this.dataSource = dataSource;
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public long save(ProductDiscountType productDiscountType) throws RepositoryException {
        return 0;
    }

    @Override
    public Optional<ProductDiscountType> findById(long id) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public List<ProductDiscountType> getAll(int limit, int offset) throws RepositoryException {
        return null;
    }

    @Override
    public boolean update(ProductDiscountType productDiscountType) throws RepositoryException {
        return false;
    }

    @Override
    public boolean delete(long id) throws RepositoryException {
        return false;
    }
}
