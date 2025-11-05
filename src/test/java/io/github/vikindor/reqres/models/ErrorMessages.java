package io.github.vikindor.reqres.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    MISSING_API_KEY("Missing API key"),
    INVALID_API_KEY("Invalid or inactive API key"),
    UNSUPPORTED_MEDIA_TYPE("unsupported_charset");

    private final String message;
}
