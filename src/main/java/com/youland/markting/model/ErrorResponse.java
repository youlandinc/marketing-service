package com.youland.markting.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

/**
 * @author chenning
 */
@Getter
@ToString
@Builder
@JsonInclude(Include.NON_EMPTY)
public class ErrorResponse {
    @Default
    private Instant timestamp = Instant.now();

    /**
     * https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
     * Http status codes: 4x, 5x
     */
    private final int status;

    /**
     * Error code that caller can use to handle the error
     */
    private final String code;

    private final String type;
    private final String message;
    private final String detail;
    private final String instance;
    private String stackTrace;
}

