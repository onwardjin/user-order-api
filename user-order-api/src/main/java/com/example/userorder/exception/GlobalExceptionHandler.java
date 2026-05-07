package com.example.userorder.exception;

import com.example.userorder.dto.common.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            UserNotFoundException.class,
            OrderNotFoundException.class,
            ProductNotFoundException.class
    })
    ResponseEntity<ErrorResponseDto> handleNotFound(RuntimeException e) {
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateLoginIdException.class)
    ResponseEntity<ErrorResponseDto> handleDuplicateValue(RuntimeException e) {
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}