package ru.clevertec.tasks.olga.exception.crud.notfound;

import ru.clevertec.tasks.olga.exception.crud.GeneralException;

public class NotFoundException extends GeneralException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

}
