package ru.clevertec.tasks.olga.exception;

public class UndefinedExceptionCustom extends CustomGeneralException {

    public UndefinedExceptionCustom(String message) {
        super(message);
    }

    public UndefinedExceptionCustom() {
        super();
    }

    public UndefinedExceptionCustom(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedExceptionCustom(Throwable cause) {
        super(cause);
    }

    protected UndefinedExceptionCustom(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
