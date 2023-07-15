package com.sekomproject.sekom.util.exceptions;

import com.sekomproject.sekom.interceptors.RequestInterceptor;
import com.sekomproject.sekom.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MissingFieldException.class)
    public ResponseEntity<Response<?>> handleMethodArgumentNotValid(MissingFieldException ex) {
        String errorMessage = ex.getExMsg();
        Map<Object, Object> errorMap = createResponse();
        Response<String> response = new Response<>(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                LocalDateTime.now(),
                errorMap
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response<?>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        String errorMessage;

        if (e.getCause().getMessage().contains("not-null property")) {
            String[] arr = e.getMessage().split("\\.");
            String fieldName = arr[arr.length - 1];
            errorMessage = fieldName + " " + ErrorMessages.MISSING_FIELD;

            Map<Object, Object> errorMap = createResponse();

            Response<String> response = new Response<>(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    errorMessage,
                    LocalDateTime.now(),
                    errorMap
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (e.getCause().getMessage().contains("duplicate key")) {
            errorMessage = ErrorMessages.DUPLICATE_CONSTRAINT;
            Map<Object, Object> errorMap = createResponse();

            Response<String> response = new Response<>(
                    HttpStatus.CONFLICT,
                    HttpStatus.CONFLICT.value(),
                    errorMessage,
                    LocalDateTime.now(),
                    errorMap
            );

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        return null;
    }

    @ExceptionHandler(BankNotFoundException.class)
    public ResponseEntity<Response<?>> handleMethodArgumentNotValid(BankNotFoundException ex) {
        String errorMessage = ex.getExMsg();

        Map<Object, Object> errorMap = createResponse();

        Response<String> response = new Response<>(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                errorMessage,
                LocalDateTime.now(),
                errorMap
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BankAccountOwnerNotFoundException.class)
    public ResponseEntity<Response<?>> handleMethodArgumentNotValid(BankAccountOwnerNotFoundException ex) {
        String errorMessage = ex.getExMsgUniqueBankAccountOwnerKey();

        Map<Object, Object> errorMap = createResponse();

        Response<String> response = new Response<>(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                errorMessage,
                LocalDateTime.now(),
                errorMap
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);


    }

    private Map<Object, Object> createResponse() {
        HttpServletRequest currentRequest = RequestInterceptor.getCurrentRequest();

        Map<Object, Object> errorMap = new HashMap<>();

        if (currentRequest != null) {
            errorMap.put("HTTP Method", currentRequest.getMethod());
            errorMap.put("Endpoint", currentRequest.getHttpServletMapping().getPattern());
            errorMap.put("Request URL", currentRequest.getRequestURL());
            errorMap.put("Request URI", currentRequest.getRequestURI());
        }

        return errorMap;
    }

}
