package ru.clevertec.tasks.olga.exception.handeled;

public class ReadingException extends HandledGeneralException {
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
