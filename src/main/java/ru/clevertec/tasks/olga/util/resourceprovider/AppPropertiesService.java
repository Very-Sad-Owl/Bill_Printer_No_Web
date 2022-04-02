package ru.clevertec.tasks.olga.util.resourceprovider;

import java.util.Locale;
import java.util.ResourceBundle;

public class AppPropertiesService {

    public static String getMessage(String messageKey) {
        return ResourceBundle.getBundle("application")
                .getString(messageKey);
    }

}
