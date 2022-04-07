package ru.clevertec.tasks.olga.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.tasks.olga.exception.crud.*;
import ru.clevertec.tasks.olga.util.localization.messagesprovider.MessageProvider;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;


@Slf4j
@ControllerAdvice("ru.clevertec.tasks.olga")
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageProvider messageProvider;
    private final MessageSource messageSource;
    private static final String ERROR_REASON_PATTERN = "(%s)";

    @Autowired
    public GlobalDefaultExceptionHandler(MessageProvider messageProvider, MessageSource messageSource) {
        this.messageProvider = messageProvider;
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public void handlerGlobal(Exception e, HttpServletResponse response, PrintWriter writer) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writer.print(messageProvider.getMessage(UndefinedExceptionHandled.class.getSimpleName())
                + e.getMessage());
    }

    @ExceptionHandler({NoRequiredArgsExceptionHandled.class, InvalidArgExceptionHandled.class})
    public ResponseEntity<?> badRequestHandler(HandledGeneralException e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler({DeletionExceptionHandled.class, UpdatingExceptionHandled.class, SavingExceptionHandled.class})
    public ResponseEntity<?> postHandler(HandledGeneralException e, Locale locale) {
        if (e.getCause().getClass() == NotFoundExceptionHandled.class) {
            String msg = messageProvider.getMessage(e.getClass().getSimpleName())
                    + ((NotFoundExceptionHandled) e.getCause().getCause()).getReasonMessage(messageSource, locale);
            return createResponseEntity(HttpStatus.NOT_FOUND, msg, (Exception) e.getCause());
        } else if (e.getCause() instanceof NotFoundExceptionHandled){
            String msg =  messageProvider.getMessage(e.getClass().getSimpleName())
                    + messageProvider.getMessage(NotFoundExceptionHandled.class.getSimpleName())
                    + ((NotFoundExceptionHandled) e.getCause()).getReasonMessage(messageSource, locale);
            return createResponseEntity(HttpStatus.NOT_FOUND, msg, new NotFoundExceptionHandled(e));
        } else {
            return createResponseEntity(HttpStatus.NOT_FOUND, e);
        }
    }

    @ExceptionHandler({NotFoundExceptionHandled.class})
    public ResponseEntity<?> notFoundHandler(NotFoundExceptionHandled e, Locale locale) {
        String msg = messageProvider.getMessage(e.getClass().getSimpleName())
                + e.getReasonMessage(messageSource, locale);
        return createResponseEntity(HttpStatus.NOT_FOUND, msg, new NotFoundExceptionHandled(e));
    }

    private ResponseEntity<?> createResponseEntity(HttpStatus status, Exception e) {
        log.warn(String.format(e.getClass().getTypeName(), e.getMessage()));
        return ResponseEntity.status(status.value())
                .body(ErrorResponse.builder()
                        .timeOccurred(Timestamp.valueOf(LocalDateTime.now()))
                        .status(status.value())
                        .errorName(status.getReasonPhrase())
                        .errorMsg(messageProvider.getMessage(e.getClass().getSimpleName()))
                        .excName(e.getClass().getName())
                        .build());
    }

    private ResponseEntity<?> createResponseEntity(HttpStatus status, String message, Exception e) {
        log.warn(String.format(e.getClass().getTypeName(), e.getMessage()));
        return ResponseEntity.status(status.value())
                .body(ErrorResponse.builder()
                        .timeOccurred(Timestamp.valueOf(LocalDateTime.now()))
                        .status(status.value())
                        .errorName(status.getReasonPhrase())
                        .errorMsg(message)
                        .excName(e.getClass().getName())
                        .build());
    }

}
