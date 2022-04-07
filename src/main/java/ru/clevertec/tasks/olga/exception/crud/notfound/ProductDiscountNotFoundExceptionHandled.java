package ru.clevertec.tasks.olga.exception.crud.notfound;

import org.springframework.context.MessageSource;
import ru.clevertec.tasks.olga.exception.crud.NotFoundExceptionHandled;

import java.util.Locale;

public class ProductDiscountNotFoundExceptionHandled extends NotFoundExceptionHandled {

    public ProductDiscountNotFoundExceptionHandled() {
        super();
    }

    public ProductDiscountNotFoundExceptionHandled(String message) {
        super(message);
    }

    public ProductDiscountNotFoundExceptionHandled(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductDiscountNotFoundExceptionHandled(Throwable cause) {
        super(cause);
    }

    @Override
    public String getReasonMessage(MessageSource messageSource, Locale locale) {
        return messageSource.getMessage("error.product_discount_id", null, locale)
                + this.getMessage();
    }
}
