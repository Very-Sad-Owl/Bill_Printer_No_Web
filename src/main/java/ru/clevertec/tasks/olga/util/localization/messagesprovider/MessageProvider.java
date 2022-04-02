package ru.clevertec.tasks.olga.util.localization.messagesprovider;

import ru.clevertec.tasks.olga.exception.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class MessageProvider {
    private Map<String, String> messages = new HashMap<>();
    private static final String BUNDLE_BASE_TITLE = "messages";

        public MessageProvider(Locale locale){
            if (locale == null){
                locale = Locale.getDefault();
            }
            ResourceBundle.clearCache();
            ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_BASE_TITLE, locale);
            messages.put(CardNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NO_CARD_MSG));
            messages.put(CartNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NO_CART_MSG));
            messages.put(CashierNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NO_CASHIER_MSG));
            messages.put(InvalidArgException.class.getSimpleName(), rb.getString(MessagesLocaleNames.INVALID_ARG_MSG));
            messages.put(NoRequiredArgsException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NO_ARGS_MSG));
            messages.put(ProductNotFoundException.class.getSimpleName(), rb.getString(MessagesLocaleNames.NO_PRODUCT_MSG));
            messages.put(ReadingException.class.getSimpleName(), rb.getString(MessagesLocaleNames.READING_EXC_MSG));
            messages.put(WritingException.class.getSimpleName(), rb.getString(MessagesLocaleNames.WRITING_EXC_MSG));
            messages.put(UndefinedException.class.getSimpleName(), rb.getString(MessagesLocaleNames.UNDEFINED_EXCEPTION_MSG));
    }

    public String getMessage(String cause){
        return messages.get(cause);
    }
}
