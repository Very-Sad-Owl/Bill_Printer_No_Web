package ru.clevertec.tasks.olga.exception;

public class ReadingException extends GeneralException {
    public ReadingException() {
        super();
    }

    public ReadingException(String message) {
        super(message);
    }

    public ReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadingException(Throwable cause) {
        super(cause);
    }

    protected ReadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
