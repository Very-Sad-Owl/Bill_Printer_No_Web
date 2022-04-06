package ru.clevertec.tasks.olga.exception.serviceexc;

import ru.clevertec.tasks.olga.exception.statusdefier.HandledGeneralException;

public class UpdatingExceptionHandled extends ServiceException {

    public UpdatingExceptionHandled() {
        super();
    }

    public UpdatingExceptionHandled(String message) {
        super(message);
    }

    public UpdatingExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdatingExceptionHandled(Throwable cause) {
        super(cause);
    }
}
