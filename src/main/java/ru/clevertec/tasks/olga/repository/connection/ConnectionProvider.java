package ru.clevertec.tasks.olga.repository.connection;

import ru.clevertec.tasks.olga.repository.connection.ecxeption.ConnectionPoolException;

public class ConnectionProvider {

    private static ConnectionPool instance;

    public static ConnectionPool getConnectionPool() throws ConnectionPoolException {
        if (instance == null) {
            try {
                instance = new ConnectionPool();
            } catch (ConnectionPoolException e) {
                throw new ConnectionPoolException(e);
            }
        }
        return instance;
    }

    public static void destroyConnectionPool() throws ConnectionPoolException {
        if (instance != null) {
            instance.dispose();
        }
    }

    private ConnectionProvider(){}
}
