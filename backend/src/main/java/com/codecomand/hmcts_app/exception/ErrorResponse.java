package com.codecomand.hmcts_app.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class ErrorResponse {
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
    private final List<Map<String, String>> fieldErrors;
}

