package com.careem.voice.notes.service.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private HttpStatus status;
    private boolean success;
    private String code;
    private String message;
    private T data;
    private List<String> errors;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data) {
        this(HttpStatus.OK, success, (String) null, message, data, (List) ((List) null));
    }

    public ApiResponse(HttpStatus status, boolean success, String message, T data) {
        this(status, success, (String) null, message, data, (List) ((List) null));
    }

    public ApiResponse(HttpStatus status, boolean success, String message, T data, String error) {
        this(status, success, (String) null, message, data, (List) Arrays.asList(error));
    }

    public ApiResponse(HttpStatus status, boolean success, String code, String message, T data) {
        this(status, success, code, message, data, (List) null);
    }

    public ApiResponse(HttpStatus status, boolean success, String code, String message, T data, String error) {
        this(status, success, code, message, data, Arrays.asList(error));
    }

    public ApiResponse(HttpStatus status, boolean success, String code, String message, T data, List<String> errors) {
        this.status = status;
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public ApiResponse(HttpStatus status, boolean success, String message, T data, List<String> errors) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }
}