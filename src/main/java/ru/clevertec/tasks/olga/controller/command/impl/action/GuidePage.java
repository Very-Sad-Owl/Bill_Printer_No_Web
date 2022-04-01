package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;
import ru.clevertec.tasks.olga.exception.GeneralException;
import ru.clevertec.tasks.olga.util.localization.MessageLocaleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class GuidePage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Locale locale = new Locale((String) request.getSession().getAttribute(LOCALE));
        MessageProvider msgProvider = new MessageProvider(locale);
        try{
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(MessageLocaleService.getMessage("label.guide", locale) + "\n");
            response.getWriter().write("localhost:8080/Controller?command=print&table=cart&id=25&language=en");
        } catch (GeneralException e) {
            response.getWriter().print(msgProvider.getMessage(e.getClass().getSimpleName()));
            log.error(e.getMessage());
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
