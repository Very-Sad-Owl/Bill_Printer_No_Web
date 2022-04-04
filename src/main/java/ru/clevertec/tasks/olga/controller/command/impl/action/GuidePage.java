package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.util.jsonmapper.JsonMapper;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;
import ru.clevertec.tasks.olga.exception.CustomGeneralException;
import ru.clevertec.tasks.olga.util.resourceprovider.MessageLocaleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;
import static ru.clevertec.tasks.olga.util.Constant.LANGUAGE;

@Slf4j
@Component
public class GuidePage implements Command {

    private MessageSource messageSource;

    @Autowired
    public GuidePage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Object execute(String body, Map<String, String[]> params) {
        return JsonMapper.parseObject(
                messageSource.getMessage("label.delete_operation_success",
                        null, new Locale(params.get(LANGUAGE)[0]
                        )));
    }
}
