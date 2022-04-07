package ru.clevertec.tasks.olga.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Builder
public class ErrorResponse {
    Timestamp timeOccurred;
    int status;
    String errorName;
    String errorMsg;
    String excName;

}
