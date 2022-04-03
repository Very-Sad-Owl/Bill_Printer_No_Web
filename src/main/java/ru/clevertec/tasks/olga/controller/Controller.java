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
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.RequestParam.CHOSEN_LANGUAGE;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
@RestController
public class Controller {

    private static final long serialVersionUID = 1L;

    @GetMapping("/")
    protected void doGet(@RequestParam(value = "command", defaultValue = "guide") String action,
                         @RequestParam(value = "table", defaultValue = "cart") String category,
						 @RequestBody Map<String, String> json) {
//		String callingUrl = request.getHeader("referer");
//		if (callingUrl == null){
//            callingUrl = "Controller?command=guide";
//		}
//		String callingCommand = callingUrl.replaceFirst("(.)*/", "");
//		log.info(callingCommand + "\n");
//		request.getSession().setAttribute("previousUrl", callingCommand);
//
        process(action, category, json);
    }

    @PostMapping("/")
    protected void doPost(@RequestParam(value = "command", defaultValue = "guide") String action,
                          @RequestParam(value = "table", defaultValue = "cart") String category,
                          @RequestBody Map<String, String> json)
            throws ServletException, IOException {
        process(json);
    }

    private void process(Map<String, String> body, String...params) throws ServletException, IOException {
//		String name;
//		String language;
//		Command command;
//
//		language = request.getParameter(CHOSEN_LANGUAGE);
//		if (language != null && !language.isEmpty()){
//			request.getSession().setAttribute(LOCALE, language);
//		} else if (request.getSession() != null
//				&& request.getSession().getAttribute(LOCALE) == null){
//			request.getSession().setAttribute(LOCALE, "en");
//		}
//
//		name = request.getParameter("command");
//		log.info("command = " + name + "\n");
//		if (name == null || name.isEmpty()) name = "guide";
//		command = CommandProvider.takeCommand(name);
//
//		log.info(String.format("%s, %s\n", request.getMethod(), name));
//
//		command.execute(request, response);
    }

}
