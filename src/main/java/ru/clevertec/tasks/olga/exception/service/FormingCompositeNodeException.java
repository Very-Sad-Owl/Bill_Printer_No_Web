package ru.clevertec.tasks.olga.exception.service;

public class FormingCompositeNodeException extends ServiceException {
    public FormingCompositeNodeException() {
        super();
    }

    public FormingCompositeNodeException(String message) {
        super(message);
    }

    public FormingCompositeNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormingCompositeNodeException(Throwable cause) {
        super(cause);
    }

    protected FormingCompositeNodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
