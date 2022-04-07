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

    @ExceptionHandler({HandledGeneralException.class})
    public void handlerService(HandledGeneralException e, HttpServletResponse response, PrintWriter writer) {
        if (e.getClass() == NoRequiredArgsExceptionHandled.class
                || e.getClass() == InvalidArgExceptionHandled.class) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(messageProvider.getMessage(e.getClass().getSimpleName()));
        } else if (e.getClass() == DeletionExceptionHandled.class
                || e.getClass() == UpdatingExceptionHandled.class
                || e.getClass() == SavingExceptionHandled.class) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            writer.print(messageProvider.getMessage(e.getClass().getSimpleName()));
        } else if (e.getClass() == NotFoundExceptionHandled.class) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.print(messageProvider.getMessage(e.getClass().getSimpleName()));
        }
    }

}
