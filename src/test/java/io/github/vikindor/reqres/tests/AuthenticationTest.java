package io.github.vikindor.reqres.tests;

import io.github.vikindor.reqres.models.ErrorResponse;
import io.github.vikindor.reqres.models.ErrorMessages;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.github.vikindor.reqres.helpers.Endpoints.*;
import static io.github.vikindor.reqres.specs.AuthenticationSpecs.*;
import static io.github.vikindor.reqres.specs.common.ResponseSpec.responseSpec;
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
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                .when()
                        .get(SINGLE_USER, userId)
        );

        step("Check that response status is 200", () ->
                response.then().spec(responseSpec(200))
        );
    }

    @Test
    @DisplayName("Should return 403 when API key is invalid")
    @Tag("Auth") @Tag("Negative")
    void shouldReturn403WhenApiKeyInvalid() {
        int userId = 999;

        ErrorResponse errorResponse = step("Make request", ()->
                given(requestWithApiKey(baseUrl, apiKeyName, "invalid"))
                .when()
                        .get(SINGLE_USER, userId)
                .then()
                        .spec(responseSpec(403))
                        .extract().as(ErrorResponse.class)
        );

        step("Check that response has expected error", ()->
                assertEquals(ErrorMessages.INVALID_API_KEY.getMessage(), errorResponse.getError())
        );
    }

    @Test
    @DisplayName("Should return 401 when API key is missing")
    @Tag("Auth") @Tag("Negative")
    void shouldReturn401WhenApiKeyMissing() {
        int userId = 999;

        ErrorResponse errorResponse = step("Make request", ()->
                given(requestWithoutApiKey(baseUrl))
                .when()
                        .get(SINGLE_USER, userId)
                .then()
                        .spec(responseSpec(401))
                        .extract().as(ErrorResponse.class)
        );

        step("Check that response has expected error", ()->
                assertEquals(ErrorMessages.MISSING_API_KEY.getMessage(), errorResponse.getError())
        );
    }
}
