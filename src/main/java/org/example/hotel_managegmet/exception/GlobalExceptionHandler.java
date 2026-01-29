package org.example.hotel_managegmet.exception;

import org.example.hotel_managegmet.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(
            AppException ex, HttpServletRequest request) {

        log.error("AppException occurred: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getRequestURI())
                .error(ex.getHttpStatus().getReasonPhrase())
                .errorCode(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .status(ex.getHttpStatus().value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        log.error("Unexpected error occurred", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getRequestURI())
                .error("Internal Server Error")
                .errorCode(ErrorCode.INTERNAL_ERROR.getCode())
                .message("Đã xảy ra lỗi hệ thống không mong muốn")
                .status(ErrorCode.INTERNAL_ERROR.getHttpStatus().value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.getHttpStatus()).body(errorResponse);
    }
}
