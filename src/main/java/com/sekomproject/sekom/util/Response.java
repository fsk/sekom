package com.sekomproject.sekom.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private HttpStatus httpStatus;
    private Integer statusCode;
    private String message;
    private LocalDateTime localDateTime;
    private T data;
    private Map<Object, Object> errorInfoMap;

    public Response(String message) {
        this.message = message;
    }

    public Response(HttpStatus httpStatus, Integer statusCode, String message) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Response(HttpStatus httpStatus, Integer statusCode, String message, T data) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public Response(HttpStatus httpStatus, Integer statusCode, String message, LocalDateTime localDateTime, Map<Object, Object> errorInfoMap) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.message = message;
        this.localDateTime = localDateTime;
        this.errorInfoMap = errorInfoMap;
    }
}
