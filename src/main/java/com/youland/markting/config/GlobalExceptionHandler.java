package com.youland.markting.config;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.youland.commons.exception.BaseException;
import com.youland.commons.model.ErrorResponse;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenning
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @Value("${youland.service.error.printStackTrace:false}")
    private boolean printStackTrace;

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(
            RuntimeException exception,
            HttpServletRequest request) {
        log.error(String.format("Unhandled Error at %s", request.getRequestURI()), exception);
        return createErrorResponse(exception, request);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(@NonNull RuntimeException exception
            , HttpServletRequest request) {
        String uri = request.getRequestURI();
        HttpStatus code = getHttpStatusCode(exception);

        String stackTrace = null;
        if (printStackTrace) {
            stackTrace = ExceptionUtils.getStackTrace(exception);
        }

        String errorCode = null;
        if (exception instanceof BaseException) {
            errorCode = ((BaseException) exception).getErrorCode();
        }
        
        var response =
                ErrorResponse.builder()
                        .type(code.name())
                        .status(code.value())
                        .code(errorCode)
                        .message(exception.getLocalizedMessage())
                        .instance(uri)
                        .stackTrace(stackTrace)
                        .build();

        return ResponseEntity.status(code).body(response);
    }


    private HttpStatus getHttpStatusCode(RuntimeException exception) {
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseStatus status;
        if (exception != null) {
            status = exception.getClass().getAnnotation(ResponseStatus.class);
            if (status != null) {
                code = status.value();
            } else if (exception instanceof HttpMessageNotReadableException) {
                code = HttpStatus.BAD_REQUEST;
            }
        }
        return code;
    }
}
