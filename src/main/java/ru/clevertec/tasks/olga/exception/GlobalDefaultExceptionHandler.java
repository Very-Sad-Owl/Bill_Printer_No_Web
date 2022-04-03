package ru.clevertec.tasks.olga.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.tasks.olga.util.resourceprovider.MessageLocaleService;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@ControllerAdvice("ru.clevertec.tasks.olga")
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handlerGlobal(Exception e, HttpServletResponse response, PrintWriter writer) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writer.print(MessageLocaleService.getMessage(UndefinedExceptionCustom.class.getSimpleName()));
    }

    @ExceptionHandler(CustomGeneralException.class)
    public void handlerCustom(CustomGeneralException e, HttpServletResponse response, PrintWriter writer){
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writer.print(MessageLocaleService.getMessage(e.getClass().getSimpleName()));
    }

}
