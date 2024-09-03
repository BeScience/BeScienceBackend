package com.mtvs.sciencemuseum.common.exception;

import com.mtvs.sciencemuseum.domain.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorType(e.getErrorCode().getErrorType());
        errorResponse.setStatus(e.getErrorCode().getStatus().value());
        errorResponse.setMessage(e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }
}
