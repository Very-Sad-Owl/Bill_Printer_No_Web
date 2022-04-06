package ru.clevertec.tasks.olga.exception.serviceexc;

import ru.clevertec.tasks.olga.exception.statusdefier.HandledGeneralException;

public class InvalidArgExceptionHandled extends HandledGeneralException {

    public InvalidArgExceptionHandled(String message) {
        super(message);
    }

}
