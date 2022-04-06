package ru.clevertec.tasks.olga.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static ru.clevertec.tasks.olga.controller.command.resource.RequestParam.CHOSEN_LANGUAGE;
import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
@RestController
@RequestMapping("/")
public class Controller {

	@GetMapping
	public String welcome(Locale loc){
		Map<String, String> params = new HashMap<>();
		params.put(CHOSEN_LANGUAGE, loc.getLanguage());
		return CommandProvider.takeCommand("guide").execute(null, params) + "";
	}

    @GetMapping("/{action}/{category}")
    @ResponseStatus(HttpStatus.OK)
    public String doGet(@RequestParam Map<String, String> params,
                        @PathVariable String action,
                        @PathVariable String category,
                        @CookieValue("user_lang") String lang
	) {
//		if (referer == null){
//            referer = "Controller?command=guide";
//		}
//		String callingCommand = referer.replaceFirst("(.)*/", "");
//		log.info(callingCommand + "\n");
//		session.setAttribute("previousUrl", callingCommand);
        log.info("locale = " + lang + "\n");
        params.put("command", action);
        params.put("table", category);
        return process(params, null, action, category);
//		return "xdxdxd";
    }

    @PostMapping
    protected String doPost(@RequestParam(value = "command", defaultValue = "guide") String action,
                          @RequestParam(value = "table") String table,
                          @RequestBody String json,
                          @RequestParam Map<String, String> params
                          ) {
        return process(params, json, action, table);
    }

    private String process(Map<String, String> params,
                         String body,
                         String commandName,
                         String category
	) {
        Command command;
//		if (language != null && !language.isEmpty()){
//			session.setAttribute(LOCALE, language);
//		} else if (session != null
//				&& session.getAttribute(LOCALE) == null){
//			session.setAttribute(LOCALE, "en");
//		}

		log.info("command = " + commandName + "\n");
		command = CommandProvider.takeCommand(commandName);

		return command.execute(body, params) + "";
    }

}
