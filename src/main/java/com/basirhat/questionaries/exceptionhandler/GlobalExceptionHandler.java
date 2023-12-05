package com.basirhat.questionaries.exceptionhandler;

import com.basirhat.questionaries.model.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllException(Exception exception) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Map.of("message", exception.getMessage()));
    }


    @ExceptionHandler({ IllegalArgumentException.class, NullPointerException.class} )
    public ErrorResponse handleIllegalArgumentException(Exception exception) {
        return ErrorResponse.builder(exception, BAD_REQUEST, exception.getMessage()).build();
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class} )
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final var validationProblemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Validation Error");
        final var errors = exception.getFieldErrors().stream().map(violation -> ConstraintViolation.builder()
                        .message(violation.getDefaultMessage())
                        .fieldName(violation.getField())
                        .rejectedValue(Objects.isNull(violation.getRejectedValue()) ? "null" : violation.getRejectedValue().toString())
                        .build())
                .toList();
        validationProblemDetail.setProperty("errors", errors);
        return validationProblemDetail;
    }

    @ExceptionHandler({ ConstraintViolationException.class,  } )
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException exception) {
        final var validationProblemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Validation Error");
        final var errors = exception.getConstraintViolations().stream().map(violation -> ConstraintViolation.builder()
                        .message(violation.getMessage())
//                        .fieldName(violation.getField())
                        .rejectedValue(Objects.isNull(violation.getInvalidValue()) ? "null" : violation.getInvalidValue().toString())
                        .build())
                .toList();
        validationProblemDetail.setProperty("errors", errors);
        return validationProblemDetail;
    }

}
