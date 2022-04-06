package ru.clevertec.tasks.olga.exception.serviceexc;

import ru.clevertec.tasks.olga.exception.statusdefier.HandledGeneralException;

public class SavingExceptionHandled extends ServiceException {

    public SavingExceptionHandled() {
        super();
    }

    public SavingExceptionHandled(String message) {
        super(message);
    }

    public SavingExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public SavingExceptionHandled(Throwable cause) {
        super(cause);
    }
}
