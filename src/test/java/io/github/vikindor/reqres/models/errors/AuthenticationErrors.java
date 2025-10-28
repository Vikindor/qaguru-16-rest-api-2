package io.github.vikindor.reqres.models.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationErrors {
    MISSING_API_KEY("Missing API key"),
    INVALID_API_KEY("Invalid or inactive API key");

    private final String message;
}
