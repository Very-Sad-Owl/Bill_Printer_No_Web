package ru.clevertec.tasks.olga.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.tasks.olga.exception.handeled.UndefinedExceptionHandled;
import ru.clevertec.tasks.olga.exception.serviceexc.*;
import ru.clevertec.tasks.olga.exception.statusdefier.*;
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

    @ExceptionHandler({HandledGeneralException.class})
    public void handlerService(HandledGeneralException e, HttpServletResponse response, PrintWriter writer) {
        if (e.getCause() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (e.getCause().getClass() == NoRequiredArgsExceptionHandled.class
                || e.getCause().getClass() == InvalidArgExceptionHandled.class) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(messageProvider.getMessage(e.getClass().getSimpleName())
                    + e.getMessage());
        } else if (e.getCause().getClass() == DeletionExceptionHandled.class
                || e.getCause().getClass() == UpdatingExceptionHandled.class
                || e.getCause().getClass() == SavingExceptionHandled.class) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            writer.print(messageProvider.getMessage(e.getClass().getSimpleName())
                    + e.getMessage());
        } else if (e.getCause().getClass() == NotFoundExceptionHandled.class) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.print(messageProvider.getMessage(e.getClass().getSimpleName())
                    + e.getMessage());
        }
    }

}
