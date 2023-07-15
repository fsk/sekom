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
        return getResponseResponseEntity(ex.getExMsg(), HttpStatus.BAD_REQUEST);
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
        return getResponseResponseEntity(ex.getExMsg(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BankAccountOwnerNotFoundException.class)
    public ResponseEntity<Response<?>> handleMethodArgumentNotValid(BankAccountOwnerNotFoundException ex) {
        return getResponseResponseEntity(ex.getExMsgUniqueBankAccountOwnerKey(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<Response<?>> handleMethodArgumentNotValid(BankAccountNotFoundException ex) {
        return getResponseResponseEntity(ex.getExtMsg(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CannotWithdrawException.class)
    public ResponseEntity<Response<?>> handleMethodArgumentNotValid(CannotWithdrawException ex) {
        return getResponseResponseEntity(ex.getExMsg(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<Response<?>> handleMethodArgumentNotValid(EmailValidationException ex) {
        return getResponseResponseEntity(ex.getExtMsg(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BankAndAccountNumberMatchException.class)
    public ResponseEntity<Response<?>> handleMethodArgumentNotValid(BankAndAccountNumberMatchException ex) {
        return getResponseResponseEntity(ex.getExtMsg(), HttpStatus.BAD_REQUEST);
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

    private ResponseEntity<Response<?>> getResponseResponseEntity(String ex, HttpStatus badRequest) {
        String errorMessage = ex;
        Map<Object, Object> errorMap = createResponse();
        Response<String> response = new Response<>(
                badRequest,
                badRequest.value(),
                errorMessage,
                LocalDateTime.now(),
                errorMap
        );
        return ResponseEntity.status(badRequest).body(response);
    }

}
