package ru.clevertec.tasks.olga.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.tasks.olga.annotation.UseCache;
import ru.clevertec.tasks.olga.entity.Product;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.RepositoryException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;
import ru.clevertec.tasks.olga.repository.ProductRepository;
import ru.clevertec.tasks.olga.repository.common.CRUDHelper;
import ru.clevertec.tasks.olga.util.tablemapper.ModelRowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static ru.clevertec.tasks.olga.repository.Query.*;

@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ModelRowMapper<Product> productWorker;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplateObject;

    @Autowired
    public ProductRepositoryImpl(ModelRowMapper<Product> productWorker, DataSource dataSource, JdbcTemplate jdbcTemplateObject) {
        this.productWorker = productWorker;
        this.dataSource = dataSource;
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    @UseCache
    public long save(Product product) throws RepositoryException {
        return 0;
    }

    @Override
    @UseCache
    public Optional<Product> findById(long id) throws RepositoryException {
        return Optional.empty();
    }

    @Override
    public List<Product> getAll(int limit, int offset) throws RepositoryException {
        return null;
    }

    @Override
    @UseCache
    public boolean update(Product product) throws RepositoryException {
        return false;
    }

    @Override
    @UseCache
    public boolean delete(long id) throws RepositoryException {
        return false;
    }
}
