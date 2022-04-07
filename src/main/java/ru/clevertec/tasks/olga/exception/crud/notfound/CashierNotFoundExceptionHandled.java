package ru.clevertec.tasks.olga.exception.crud.notfound;

import org.springframework.context.MessageSource;
import ru.clevertec.tasks.olga.exception.crud.NotFoundExceptionHandled;

import java.util.Locale;

public class CashierNotFoundExceptionHandled extends NotFoundExceptionHandled {

    public CashierNotFoundExceptionHandled() {
        super();
    }

    public CashierNotFoundExceptionHandled(String message) {
        super(message);
    }

    public CashierNotFoundExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public CashierNotFoundExceptionHandled(Throwable cause) {
        super(cause);
    }

    @Override
    public String getReasonMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage("error.cashier_id", null, locale)
                + this.getMessage();
    }
}
