package ru.clevertec.tasks.olga.util.localization.messagesprovider;

import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.exception.handeled.*;
import ru.clevertec.tasks.olga.exception.repoexc.ReadingException;
import ru.clevertec.tasks.olga.exception.repoexc.WritingException;
import ru.clevertec.tasks.olga.exception.serviceexc.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static ru.clevertec.tasks.olga.util.localization.messagesprovider.MessagesLocaleNames.*;

@Component
public class MessageProvider {
    private Map<String, String> messages = new HashMap<>();
    private static final String BUNDLE_BASE_TITLE = "messages";

    @PostConstruct
    public void init(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        ResourceBundle.clearCache();
        ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_BASE_TITLE, locale);
        messages.put(NotFoundExceptionHandled.class.getSimpleName(), rb.getString(NOT_FOUND_MSG));
        messages.put(SavingExceptionHandled.class.getSimpleName(), rb.getString(SAVING_MSG));
        messages.put(UpdatingExceptionHandled.class.getSimpleName(), rb.getString(UPDATING_MSG));
        messages.put(DeletionExceptionHandled.class.getSimpleName(), rb.getString(DELETING_MSG));
        messages.put(InvalidArgExceptionHandled.class.getSimpleName(), rb.getString(INVALID_ARG_MSG));
        messages.put(NoRequiredArgsExceptionHandled.class.getSimpleName(), rb.getString(NO_ARGS_MSG));
        messages.put(ReadingException.class.getSimpleName(), rb.getString(READING_EXC_MSG));
        messages.put(WritingException.class.getSimpleName(), rb.getString(WRITING_EXC_MSG));
        messages.put(UndefinedExceptionHandled.class.getSimpleName(), rb.getString(UNDEFINED_EXCEPTION_MSG));
    }

    public String getMessage(String cause) {
        return messages.get(cause);
    }
}
