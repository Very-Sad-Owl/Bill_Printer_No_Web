package ru.clevertec.tasks.olga.exception;

import ru.clevertec.tasks.olga.util.MessageLocaleService;

import java.util.Locale;

public class LocalizedException extends RuntimeException {

    public LocalizedException() {
        super();
    }

    public LocalizedException(String message) {
        super(message);
    }

    public LocalizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalizedException(Throwable cause) {
        super(cause);
    }

    protected LocalizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
