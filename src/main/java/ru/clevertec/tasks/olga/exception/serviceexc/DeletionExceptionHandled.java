package ru.clevertec.tasks.olga.exception.serviceexc;

import ru.clevertec.tasks.olga.exception.statusdefier.HandledGeneralException;

public class DeletionExceptionHandled extends ServiceException {

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
