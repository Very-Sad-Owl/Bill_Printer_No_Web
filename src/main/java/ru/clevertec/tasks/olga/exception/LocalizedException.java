package ru.clevertec.tasks.olga.exception;

import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.Locale;

public class LocalizedException extends RuntimeException {

    private final String messageKey;
    private final Locale locale;

    public LocalizedException(String messageKey) {
        this(messageKey, Locale.getDefault());
    }

    public LocalizedException(String messageKey, Locale locale) {
        this.messageKey = messageKey;
        this.locale = locale;
    }

    public String getLocalizedMessage() {
        return MessageLocaleService.getMessage(messageKey, locale);
    }
}
