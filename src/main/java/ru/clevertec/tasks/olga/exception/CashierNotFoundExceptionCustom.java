package ru.clevertec.tasks.olga.exception;

public class CashierNotFoundExceptionCustom extends CustomGeneralException {
    public CashierNotFoundExceptionCustom() {
        super();
    }

    public CashierNotFoundExceptionCustom(String message) {
        super(message);
    }

    public CashierNotFoundExceptionCustom(String message, Throwable cause) {
        super(message, cause);
    }

    public CashierNotFoundExceptionCustom(Throwable cause) {
        super(cause);
    }

    protected CashierNotFoundExceptionCustom(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
