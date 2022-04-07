package ru.clevertec.tasks.olga.exception.crud.notfound;

import org.springframework.context.MessageSource;
import ru.clevertec.tasks.olga.exception.crud.NotFoundExceptionHandled;

import java.util.Locale;

public class CardNotFoundExceptionHandled extends NotFoundExceptionHandled {

    public CardNotFoundExceptionHandled() {
        super();
    }

    public CardNotFoundExceptionHandled(String message) {
        super(message);
    }

    public CardNotFoundExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public CardNotFoundExceptionHandled(Throwable cause) {
        super(cause);
    }

    @Override
    public String getReasonMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage("error.card_id", null, locale)
                + this.getMessage();
    }
}
