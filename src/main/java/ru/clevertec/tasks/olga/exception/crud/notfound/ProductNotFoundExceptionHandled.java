package ru.clevertec.tasks.olga.exception.crud.notfound;

import org.springframework.context.MessageSource;
import ru.clevertec.tasks.olga.exception.crud.NotFoundExceptionHandled;

import java.util.Locale;

public class ProductNotFoundExceptionHandled extends NotFoundExceptionHandled {

    public ProductNotFoundExceptionHandled() {
        super();
    }

    public ProductNotFoundExceptionHandled(String message) {
        super(message);
    }

    public ProductNotFoundExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundExceptionHandled(Throwable cause) {
        super(cause);
    }

    @Override
    public String getReasonMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage("error.product_id", null, locale)
                + this.getMessage();
    }
}
