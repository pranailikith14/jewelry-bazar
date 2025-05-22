package com.jewelry.jewelrystore_microservice.exception;

import com.jewelry.jewelrystore_microservice.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now().toString(),null);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now().toString(),null);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", LocalDateTime.now().toString(),null);
    }

    private ResponseEntity<ApiResponse<Object>> buildResponse(HttpStatus status, String message, String timeStamp, Object data) {
        ApiResponse<Object> response = new ApiResponse<Object>(status.value(),message,timeStamp,data);
        response.setStatus(status.value());
        response.setMessage(message);
        response.setTimeStamp(timeStamp);
        response.setData(data);
        return new ResponseEntity<>(response, status);
    }

}

