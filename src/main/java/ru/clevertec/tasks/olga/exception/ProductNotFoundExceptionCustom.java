package ru.clevertec.tasks.olga.exception;

public class ProductNotFoundExceptionCustom extends CustomGeneralException {
    public ProductNotFoundExceptionCustom() {
        super();
    }

    public ProductNotFoundExceptionCustom(String message) {
        super(message);
    }

    public ProductNotFoundExceptionCustom(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundExceptionCustom(Throwable cause) {
        super(cause);
    }

    protected ProductNotFoundExceptionCustom(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
