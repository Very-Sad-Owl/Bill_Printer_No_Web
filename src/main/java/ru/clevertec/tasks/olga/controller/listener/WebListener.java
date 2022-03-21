package ru.clevertec.tasks.olga.controller.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import ru.clevertec.tasks.olga.repository.connection.ConnectionPool;
import ru.clevertec.tasks.olga.repository.connection.ConnectionProvider;
import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class WebListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionProvider.getConnectionPool();
        } catch (ConnectionPoolException e) {
            log.error("Error while creating connection pool");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionProvider.getConnectionPool().dispose();
        } catch (ConnectionPoolException e) {
            log.error("Error while closing connection pool");
        }
    }
}