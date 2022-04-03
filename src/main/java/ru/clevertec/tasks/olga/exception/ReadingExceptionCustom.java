package ru.clevertec.tasks.olga.exception;

public class ReadingExceptionCustom extends CustomGeneralException {
    public ReadingExceptionCustom() {
        super();
    }

    public ReadingExceptionCustom(String message) {
        super(message);
    }

    public ReadingExceptionCustom(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadingExceptionCustom(Throwable cause) {
        super(cause);
    }

    protected ReadingExceptionCustom(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
