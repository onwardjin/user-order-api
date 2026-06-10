package io.github.blairjin.user_order_api.exception;

import io.github.blairjin.user_order_api.dto.common.ErrorResponse;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InsufficientStockException;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidLoginException;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidTokenException;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;
import io.github.blairjin.user_order_api.exception.CONFLICT.DuplicateLoginIdException;
import io.github.blairjin.user_order_api.exception.LOCKED.AccountLockedException;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            InvalidLoginException.class,
            InvalidTokenException.class,
            InsufficientStockException.class,
            InvalidValueException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException e
    ) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                message
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            UserProfileNotFoundException.class,
            UserAddressNotFoundException.class,
            ProductNotFoundException.class,
            OrderNotFoundException.class,
            OrderItemNotFoundException.class,
            CartNotFoundException.class,
            CartItemNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({
            DuplicateLoginIdException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ErrorResponse> handleLocked(RuntimeException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.LOCKED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.LOCKED).body(response);
    }
}