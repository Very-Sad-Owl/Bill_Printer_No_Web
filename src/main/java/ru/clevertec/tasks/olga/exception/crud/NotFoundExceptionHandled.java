package ru.clevertec.tasks.olga.exception.crud;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class NotFoundExceptionHandled extends HandledGeneralException {

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


    public String getReasonMessage(MessageSource messageSource, Locale locale){
        return super.getMessage();
    }


}
