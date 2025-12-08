package com.codecomand.hmcts_app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TASK_CREATION_FAILED("TASK_CREATION_FAILED", "An unexpected error occurred while creating the task", HttpStatus.INTERNAL_SERVER_ERROR);


    private final  String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCode(final String code,
              final String defaultMessage,
              final HttpStatus httpStatus) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }
}
