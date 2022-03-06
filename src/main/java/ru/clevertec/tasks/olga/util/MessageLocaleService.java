package ru.clevertec.tasks.olga.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageLocaleService {

    public static String getMessage(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("messages", locale)
                .getString(messageKey);
    }

    public static String getMessage(String messageKey) {
        return ResourceBundle.getBundle("messages", Locale.getDefault())
                .getString(messageKey);
    }

}
