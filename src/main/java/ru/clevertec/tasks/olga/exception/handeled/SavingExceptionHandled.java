package ru.clevertec.tasks.olga.exception.handeled;

public class SavingExceptionHandled extends HandledGeneralException {

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
