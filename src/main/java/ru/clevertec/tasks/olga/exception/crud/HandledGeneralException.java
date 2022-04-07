package ru.clevertec.tasks.olga.exception.crud;

public class HandledGeneralException extends RuntimeException {

    public HandledGeneralException() {
        super();
    }

    public HandledGeneralException(String message) {
        super(message);
    }

    public HandledGeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandledGeneralException(Throwable cause) {
        super(cause);
    }

    protected HandledGeneralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
