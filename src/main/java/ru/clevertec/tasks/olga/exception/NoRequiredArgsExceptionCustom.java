package ru.clevertec.tasks.olga.exception;

public class NoRequiredArgsExceptionCustom extends CustomGeneralException {
    public NoRequiredArgsExceptionCustom() {
        super();
    }

    public NoRequiredArgsExceptionCustom(String message) {
        super(message);
    }

    public NoRequiredArgsExceptionCustom(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRequiredArgsExceptionCustom(Throwable cause) {
        super(cause);
    }

    protected NoRequiredArgsExceptionCustom(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
