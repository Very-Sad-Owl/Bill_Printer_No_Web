package ru.clevertec.tasks.olga.util.resourceprovider;

import java.util.ResourceBundle;
public class DbResourceService {
    private final static DbResourceService instance = new DbResourceService();

    private final ResourceBundle bundle =
            ResourceBundle.getBundle("db");

    public static DbResourceService getInstance() {
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
