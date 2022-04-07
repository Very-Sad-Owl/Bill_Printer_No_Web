package ru.clevertec.tasks.olga.exception.crud.notfound;

import org.springframework.context.MessageSource;
import ru.clevertec.tasks.olga.exception.crud.HandledGeneralException;
import ru.clevertec.tasks.olga.exception.crud.NotFoundExceptionHandled;

import java.util.Locale;

public class BillNotFoundExceptionHandled extends NotFoundExceptionHandled {

    public BillNotFoundExceptionHandled() {
        super();
    }

    public BillNotFoundExceptionHandled(String message) {
        super(message);
    }

    public BillNotFoundExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public BillNotFoundExceptionHandled(Throwable cause) {
        super(cause);
    }

    @Override
    public String getReasonMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage("error.bill_id", null, locale)
                + this.getMessage();
    }
}
