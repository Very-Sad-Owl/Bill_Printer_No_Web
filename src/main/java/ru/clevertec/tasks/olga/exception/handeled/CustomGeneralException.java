package ru.clevertec.tasks.olga.exception.handeled;

public class CustomGeneralException extends Exception {

    public CustomGeneralException() {
        super();
    }

    public CustomGeneralException(String message) {
        super(message);
    }

    public CustomGeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomGeneralException(Throwable cause) {
        super(cause);
    }

    protected CustomGeneralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
