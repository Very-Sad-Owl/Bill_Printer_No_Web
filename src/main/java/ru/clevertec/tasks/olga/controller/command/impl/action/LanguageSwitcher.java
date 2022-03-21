package ru.clevertec.tasks.olga.controller.command.impl.action;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import ru.clevertec.tasks.olga.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ru.clevertec.tasks.olga.controller.command.resource.RequestParam.*;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
public class LanguageSwitcher implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String chosenLang = request.getParameter(CHOSEN_LANGUAGE);

        HttpSession session = request.getSession();

        if(session != null) {
            session.setAttribute(LOCALE, chosenLang);
        }
        log.info("language switched to " + chosenLang);
        response.sendRedirect("Controller?command=guide");
//        response.sendRedirect((String) request.getSession().getAttribute(PREVIOUS_URL));
    }
}
