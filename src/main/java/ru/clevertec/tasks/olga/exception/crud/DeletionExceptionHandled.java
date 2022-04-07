package ru.clevertec.tasks.olga.exception.crud;

public class DeletionExceptionHandled extends HandledGeneralException {

    public DeletionExceptionHandled() {
        super();
    }

    public DeletionExceptionHandled(String message) {
        super(message);
    }

    public DeletionExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public DeletionExceptionHandled(Throwable cause) {
        super(cause);
    }
}
