package ru.clevertec.tasks.olga.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.controller.command.CommandProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.RequestParam.CHOSEN_LANGUAGE;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
@RestController
@RequestMapping("/")
public class Controller {

    @GetMapping
    protected void doGet(@RequestParam(value = "command", defaultValue = "guide") String action,
                         @RequestParam(value = "table") String table,
                         @RequestParam(value = "language", defaultValue = "en") String lang,
						 @RequestBody String json,
                         @RequestParam Map<String, String[]> params,
                         @RequestHeader("referer") String referer,
                         HttpSession session) {
		if (referer == null){
            referer = "Controller?command=guide";
		}
		String callingCommand = referer.replaceFirst("(.)*/", "");
		log.info(callingCommand + "\n");
		session.setAttribute("previousUrl", callingCommand);

        process(params, json, action, table, lang, session);
    }

    @PostMapping
    protected void doPost(@RequestParam(value = "command", defaultValue = "guide") String action,
                          @RequestParam(value = "table") String table,
                          @RequestParam(value = "language", defaultValue = "en") String lang,
                          @RequestBody String json,
                          @RequestParam Map<String, String[]> params,
                          HttpSession session) {
        process(params, json, action, table, lang, session);
    }

    private void process(Map<String, String[]> params,
                         String body,
                         String commandName,
                         String category,
                         String language,
                         HttpSession session) {
        Command command;
		if (language != null && !language.isEmpty()){
			session.setAttribute(LOCALE, language);
		} else if (session != null
				&& session.getAttribute(LOCALE) == null){
			session.setAttribute(LOCALE, "en");
		}

		log.info("command = " + commandName + "\n");
		command = CommandProvider.takeCommand(commandName);

		command.execute(body, params);
    }

}
