package ru.clevertec.tasks.olga.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.tasks.olga.exception.handeled.*;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@ControllerAdvice("ru.clevertec.tasks.olga")
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageProvider messageProvider;

    @Autowired
    public GlobalDefaultExceptionHandler(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    @ExceptionHandler(Exception.class)
    public void handlerGlobal(Exception e, HttpServletResponse response, PrintWriter writer) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writer.print(messageProvider.getMessage(UndefinedExceptionHandled.class.getSimpleName())
                + e.getMessage());
    }

    @ExceptionHandler({NoRequiredArgsExceptionHandled.class, InvalidArgExceptionHandled.class})
    public void badRequestHandler(HandledGeneralException e, HttpServletResponse response, PrintWriter writer) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writer.print(messageProvider.getMessage(e.getClass().getSimpleName()));
    }

    @ExceptionHandler({DeletionExceptionHandled.class, UpdatingExceptionHandled.class, SavingExceptionHandled.class})
    public void postHandler(HandledGeneralException e, HttpServletResponse response, PrintWriter writer) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        writer.print(messageProvider.getMessage(e.getClass().getSimpleName()));
    }

    @ExceptionHandler({NotFoundExceptionHandled.class})
    public void notFoundHandler(HandledGeneralException e, HttpServletResponse response, PrintWriter writer) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        writer.print(messageProvider.getMessage(e.getClass().getSimpleName()));
    }

}
