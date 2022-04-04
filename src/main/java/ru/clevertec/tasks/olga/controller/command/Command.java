package ru.clevertec.tasks.olga.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface Command {
	
	Object execute(String body, Map<String, String[]> params);

}
