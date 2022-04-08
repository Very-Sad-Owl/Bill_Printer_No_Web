package ru.clevertec.tasks.olga.util.localization;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.exception.crud.notfound.*;
import ru.clevertec.tasks.olga.repository.exception.ReadingException;
import ru.clevertec.tasks.olga.repository.exception.WritingException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Component
public class MessageProvider {
    private final Map<String, String> messages = new HashMap<>();
    private static final String BUNDLE_BASE_TITLE = "messages";

    @PostConstruct
    public void init() {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle.clearCache();
        ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_BASE_TITLE, locale);
        messages.put(NotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NOT_FOUND_MSG));
        messages.put(BillNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NOT_FOUND_BILL_MSG));
        messages.put(CardNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NOT_FOUND_CARD_MSG));
        messages.put(CashierNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NOT_FOUND_CASHIER_MSG));
        messages.put(ProductNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NOT_FOUND_PRODUCT_MSG));
        messages.put(ProductDiscountNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NOT_FOUND_PRODUCT_DISCOUNT_MSG));
        messages.put(CardTypeNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NOT_FOUND_CARD_TYPE_MSG));
        messages.put(SavingException.class.getSimpleName(), rb.getString(MessagesLocaleNames.SAVING_MSG));
        messages.put(UpdatingException.class.getSimpleName(), rb.getString(MessagesLocaleNames.UPDATING_MSG));
        messages.put(DeletionException.class.getSimpleName(), rb.getString(MessagesLocaleNames.DELETING_MSG));
        messages.put(InvalidArgException.class.getSimpleName(), rb.getString(MessagesLocaleNames.INVALID_ARG_MSG));
        messages.put(NoRequiredArgsException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NO_ARGS_MSG));
        messages.put(ReadingException.class.getSimpleName(), rb.getString(MessagesLocaleNames.READING_EXC_MSG));
        messages.put(WritingException.class.getSimpleName(), rb.getString(MessagesLocaleNames.WRITING_EXC_MSG));
        messages.put(UndefinedException.class.getSimpleName(), rb.getString(MessagesLocaleNames.UNDEFINED_EXCEPTION_MSG));
    }

    public String getMessage(String cause) {
        return messages.get(cause);
    }
}
