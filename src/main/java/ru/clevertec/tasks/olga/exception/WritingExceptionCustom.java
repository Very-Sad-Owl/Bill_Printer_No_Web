package ru.clevertec.tasks.olga.exception;

public class WritingExceptionCustom extends CustomGeneralException {
    public WritingExceptionCustom() {
        super();
    }

    public WritingExceptionCustom(String message) {
        super(message);
    }

    public WritingExceptionCustom(String message, Throwable cause) {
        super(message, cause);
    }

    public WritingExceptionCustom(Throwable cause) {
        super(cause);
    }

    protected WritingExceptionCustom(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
