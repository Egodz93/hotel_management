package org.example.hotel_managegmet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;
    private final Object[] args;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.args = null;
    }

    public AppException(ErrorCode errorCode, Object... args) {

        super(String.format(errorCode.getMessage(), args));
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.args = args;
    }

}
