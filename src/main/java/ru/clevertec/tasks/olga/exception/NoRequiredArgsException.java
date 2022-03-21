package ru.clevertec.tasks.olga.exception;

public class NoRequiredArgsException extends LocalizedException {
    public NoRequiredArgsException() {
        super();
    }

    public NoRequiredArgsException(String message) {
        super(message);
    }

    public NoRequiredArgsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRequiredArgsException(Throwable cause) {
        super(cause);
    }

    protected NoRequiredArgsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
