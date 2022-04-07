package ru.clevertec.tasks.olga.exception.crud;

public class UndefinedExceptionHandled extends HandledGeneralException {

    public UndefinedExceptionHandled(String message) {
        super(message);
    }

    public UndefinedExceptionHandled() {
        super();
    }

    public UndefinedExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedExceptionHandled(Throwable cause) {
        super(cause);
    }

    protected UndefinedExceptionHandled(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
