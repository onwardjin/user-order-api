package com.example.demo.exception;

import com.example.demo.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException e){
        ErrorResponseDto response = new ErrorResponseDto(404, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFound(OrderNotFoundException o){
        ErrorResponseDto response = new ErrorResponseDto(404, o.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}