package ru.clevertec.tasks.olga.exception.serviceexc;

import ru.clevertec.tasks.olga.exception.statusdefier.HandledGeneralException;

public class NotFoundExceptionHandled extends ServiceException {

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
