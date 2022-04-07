package ru.clevertec.tasks.olga.exception.handeled;

public class NotFoundExceptionHandled extends HandledGeneralException {

    public NotFoundExceptionHandled() {
        super();
    }

    public NotFoundExceptionHandled(String message) {
        super(message);
    }

    public NotFoundExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundExceptionHandled(Throwable cause) {
        super(cause);
    }
}
