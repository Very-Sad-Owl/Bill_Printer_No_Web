package ru.clevertec.tasks.olga.repository.connection.recource_provider;

import java.util.ResourceBundle;
public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();

    private final ResourceBundle bundle =
            ResourceBundle.getBundle("db");

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
