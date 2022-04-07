package ru.clevertec.tasks.olga.exception.handeled;

public class InvalidArgExceptionHandled extends HandledGeneralException {

    public InvalidArgExceptionHandled(String message) {
        super(message);
    }

    public InvalidArgExceptionHandled() {
        super();
    }

    public InvalidArgExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgExceptionHandled(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
