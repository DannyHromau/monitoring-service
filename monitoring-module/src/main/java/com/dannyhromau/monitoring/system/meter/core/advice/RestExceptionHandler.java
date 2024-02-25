package com.dannyhromau.monitoring.system.meter.core.advice;

import com.dannyhromau.monitoring.system.meter.api.dto.ErrorMessageDto;
import com.dannyhromau.monitoring.system.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.system.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.system.meter.exception.UnAuthorizedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<ErrorMessageDto> notFoundHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageDto(e.getMessage()));
    }

    @ExceptionHandler({InvalidDataException.class, IllegalArgumentException.class})
    protected ResponseEntity<ErrorMessageDto> invalidDataHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageDto(e.getMessage()));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorMessageDto> duplicatedDataHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessageDto(e.getMessage()));
    }

    @ExceptionHandler({SQLException.class})
    protected ResponseEntity<ErrorMessageDto> dbProblemsHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorMessageDto(e.getMessage()));
    }

    @ExceptionHandler({UnAuthorizedException.class})
    protected ResponseEntity<ErrorMessageDto> unAuthorizedHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessageDto(e.getMessage()));
    }
}
