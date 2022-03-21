package ru.clevertec.tasks.olga.controller.recource_provider;

import java.util.ResourceBundle;

public class FolderResourceManager {
    private final static FolderResourceManager instance = new FolderResourceManager();

    private ResourceBundle bundle =
            ResourceBundle.getBundle("folders");

    public static FolderResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
