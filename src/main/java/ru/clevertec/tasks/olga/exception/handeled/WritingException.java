package ru.clevertec.tasks.olga.exception.handeled;

public class WritingException extends HandledGeneralException {
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

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
