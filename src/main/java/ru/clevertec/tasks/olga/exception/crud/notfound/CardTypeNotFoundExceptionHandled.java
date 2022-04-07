package ru.clevertec.tasks.olga.exception.crud.notfound;

import org.springframework.context.MessageSource;
import ru.clevertec.tasks.olga.exception.crud.NotFoundExceptionHandled;

import java.util.Locale;

public class CardTypeNotFoundExceptionHandled extends NotFoundExceptionHandled {

    public CardTypeNotFoundExceptionHandled() {
        super();
    }

    public CardTypeNotFoundExceptionHandled(String message) {
        super(message);
    }

    public CardTypeNotFoundExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public CardTypeNotFoundExceptionHandled(Throwable cause) {
        super(cause);
    }

    @Override
    public String getReasonMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage("error.card_type_id", null, locale)
                + this.getMessage();
    }
}
