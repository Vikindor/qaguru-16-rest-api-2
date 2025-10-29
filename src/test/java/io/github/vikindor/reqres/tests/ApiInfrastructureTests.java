package io.github.vikindor.reqres.tests;

import io.github.vikindor.reqres.helpers.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.github.vikindor.reqres.helpers.Endpoints.USERS;
import static io.github.vikindor.reqres.specs.ApiInfrastructureSpecs.*;
import static io.github.vikindor.reqres.specs.AuthenticationSpecs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("API Infrastructure and Technical Validation Tests")
@Tag("Infrastructure") @Tag("Regression")
public class ApiInfrastructureTests extends TestBase{

    @Test
    @DisplayName("Should respond within acceptable time threshold")
    @Tag("Performance") @Tag("Smoke")
    void shouldHaveFastResponseTime() {
        Response response = step("Make request", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                .when()
                        .get(USERS)
        );

        step("Check that response status is 200", () ->
                response.then().spec(ok200ResponseSpec())
        );

        step("Check that response time is <1500 ms", () ->
                assertThat(response.time() < 1500)
        );
    }

    @Test
    @DisplayName("Should return 304 when ETag matches (resource not modified)")
    @Tag("Etag")
    void shouldReturn304WhenResourceNotChanged() {
        String response = step("Make request and extract ETag", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                .when()
                        .get(USERS)
                .then()
                        .spec(ok200ResponseSpec())
                        .extract().header(Headers.ETAG)
        );

        step("Check that if header has 'If-None-Match' then response status is 304", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                        .header(Headers.IF_NONE_MATCH, response)
                .when()
                        .get(USERS)
                .then()
                        .spec(notModified304ResponseSpec())
        );
    }
}
