package ru.clevertec.tasks.olga.controller;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.tasks.olga.controller.command.Command;
import ru.clevertec.tasks.olga.controller.command.CommandProvider;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.clevertec.tasks.olga.controller.command.resource.SessionAttr.LOCALE;

@Slf4j
@WebServlet("/")
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final CommandProvider provider = new CommandProvider();

	public Controller() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String callingUrl = request.getHeader("referer");
		if (callingUrl == null){
//			callingUrl = CommandUrlPath.GOTOINDEXPAGE;
            callingUrl = "Controller?command=guide";
		}
		String callingCommand = callingUrl.replaceFirst("(.)*/", "");
		log.info(callingCommand + "\n");
		request.getSession().setAttribute("previousUrl", callingCommand);

		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name;
		Command command;

		if (request.getSession() != null){
		    request.getSession().setAttribute(LOCALE, "en");
        }

		name = request.getParameter("command");
		log.info("command = " + name + "\n");
		if (name == null || name.isEmpty()) name = "guide";
		command = provider.takeCommand(name);

		log.info(String.format("%s, %s\n", request.getMethod(), name));

		command.execute(request, response);
	}

}
