package ru.clevertec.tasks.olga.exception.crud;

public class NoRequiredArgsExceptionHandled extends HandledGeneralException {
    public NoRequiredArgsExceptionHandled() {
        super();
    }

    public NoRequiredArgsExceptionHandled(String message) {
        super(message);
    }

    public NoRequiredArgsExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRequiredArgsExceptionHandled(Throwable cause) {
        super(cause);
    }

    protected NoRequiredArgsExceptionHandled(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
