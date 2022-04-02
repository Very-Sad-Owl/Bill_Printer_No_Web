package ru.clevertec.tasks.olga.exception;

public class WritingException extends GeneralException {
    public WritingException() {
        super();
    }

    public WritingException(String message) {
        super(message);
    }

    public WritingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WritingException(Throwable cause) {
        super(cause);
    }

    protected WritingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
