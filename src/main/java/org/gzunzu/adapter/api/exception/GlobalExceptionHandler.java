package org.gzunzu.adapter.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpError> handleVariousErrors(final Exception exception) {
        final HttpStatus status;

        if (exception instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return buildErrorResponse(exception, status);
    }

    private ResponseEntity<HttpError> buildErrorResponse(final Exception exception, final HttpStatus status) {
        final HttpError body = new HttpError(LocalDateTime.now(), status.value(), exception.getMessage());
        return new ResponseEntity<>(body, status);
    }
}
