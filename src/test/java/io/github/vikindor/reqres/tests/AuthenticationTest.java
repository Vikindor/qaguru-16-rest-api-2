package io.github.vikindor.reqres.tests;

import io.github.vikindor.reqres.models.common.ErrorResponse;
import io.github.vikindor.reqres.models.errors.AuthenticationErrors;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.github.vikindor.reqres.helpers.Endpoints.*;
import static io.github.vikindor.reqres.specs.AuthenticationSpecs.*;
import static io.github.vikindor.reqres.specs.ApiInfrastructureSpecs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Authentication and Authorization Tests")
@Tag("Auth") @Tag("Regression")
public class AuthenticationTest extends TestBase{

    @Test
    @DisplayName("Should return 200 when API key is valid")
    @Tag("Auth") @Tag("Positive") @Tag("Smoke")
    void shouldReturn200WhenApiKeyValid() {
        int userId = 1;

        Response response = step("Make request", () ->
        given(requestWithApiKey(baseLink, apiKeyName, apiKeyValue))
        .when()
                .get(SINGLE_USER, userId)
        );

        step("Check response status 200", () ->
                response.then().spec(ok200ResponseSpec())
        );
    }

    @Test
    @DisplayName("Should return 403 when API key is invalid")
    @Tag("Auth") @Tag("Negative")
    void shouldReturn403WhenApiKeyInvalid() {
        int userId = 999;

        ErrorResponse errorResponse = step("Make request", ()->
        given(requestWithApiKey(baseLink, apiKeyName, "invalid"))
        .when()
                .get(SINGLE_USER, userId)
        .then()
                .spec(forbidden403ResponseSpec())
                .extract().as(ErrorResponse.class)
        );

        step("Check that response has error", ()->
                assertEquals(AuthenticationErrors.INVALID_API_KEY.getMessage(), errorResponse.getError())
        );
    }

    @Test
    @DisplayName("Should return 401 when API key is missing")
    @Tag("Auth") @Tag("Negative")
    void shouldReturn401WhenApiKeyMissing() {
        int userId = 999;

        ErrorResponse errorResponse = step("Make request", ()->
        given(requestWithoutApiKey(baseLink))
        .when()
                .get(SINGLE_USER, userId)
        .then()
                .spec(unauthorized401ResponseSpec())
                .extract().as(ErrorResponse.class)
        );

        step("Check that response has error", ()->
                assertEquals(AuthenticationErrors.MISSING_API_KEY.getMessage(), errorResponse.getError())
        );
    }
}
