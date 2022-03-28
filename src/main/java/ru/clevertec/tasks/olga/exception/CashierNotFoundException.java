package ru.clevertec.tasks.olga.exception;

public class CashierNotFoundException extends GeneralException {
    public CashierNotFoundException() {
        super();
    }

    public CashierNotFoundException(String message) {
        super(message);
    }

    public CashierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CashierNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CashierNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
