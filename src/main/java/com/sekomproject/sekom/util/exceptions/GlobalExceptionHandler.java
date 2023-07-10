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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response<?>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        String errorMessage = ErrorMessages.DUPLICATE_CONSTRAINT;

        HttpServletRequest currentRequest = RequestInterceptor.getCurrentRequest();

        Map<Object, Object> errorMap = new HashMap<>();

        if (currentRequest != null) {
            errorMap.put("HTTP Method", currentRequest.getMethod());
            errorMap.put("Endpoint", currentRequest.getHttpServletMapping().getPattern());
            errorMap.put("Request URL", currentRequest.getRequestURL());
            errorMap.put("Request URI", currentRequest.getRequestURI());
        }

        Response<String> response = new Response<>(
                HttpStatus.CONFLICT,
                HttpStatus.CONFLICT.value(),
                errorMessage,
                LocalDateTime.now(),
                errorMap
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
