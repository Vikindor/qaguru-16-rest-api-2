package io.github.vikindor.reqres.tests;

import io.github.vikindor.reqres.models.users.UpdateUserRequestModel;
import io.github.vikindor.reqres.models.users.UpdateUserResponseModel;
import io.github.vikindor.reqres.models.users.UserListItemResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.vikindor.reqres.helpers.Endpoints.SINGLE_USER;
import static io.github.vikindor.reqres.helpers.Endpoints.USERS;
import static io.github.vikindor.reqres.specs.AuthenticationSpecs.*;
import static io.github.vikindor.reqres.specs.ApiInfrastructureSpecs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Users Endpoint Tests")
@Tag("Users") @Tag("Regression")
public class UsersTests extends TestBase {

    @Test
    @DisplayName("Should validate basic user fields")
    @Tag("Users") @Tag("Positive") @Tag("Smoke")
    void shouldValidateBasicFieldsForUser() {
        List<UserListItemResponse> users = step("Make request", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                .when()
                        .get(USERS)
                .then()
                        .spec(ok200ResponseSpec())
                        .extract().jsonPath().getList("data", UserListItemResponse.class)
        );

        step("Check that all fields are valid", () -> {
            assertThat(users).allSatisfy(u -> {
                assertThat(u.getId()).isPositive();
                assertThat(u.getEmail()).contains("@");
                assertThat(u.getFirstName()).isNotBlank();
                assertThat(u.getLastName()).isNotBlank();
                assertThat(u.getAvatar()).isNotBlank();
            });
        });
    }

    @Test
    @DisplayName("Should return empty list when page is beyond total")
    @Tag("Pagination") @Tag("Positive")
    void shouldReturnEmptyListWhenPageBeyondTotal() {
        List<UserListItemResponse> users = step("Make request", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                        .queryParam("page", 9999)
                .when()
                        .get(USERS)
                .then()
                        .spec(ok200ResponseSpec())
                        .extract().jsonPath().getList("data", UserListItemResponse.class)
        );

        step("Check that response body is empty", () -> {
            assertThat(users).isEmpty();
        });
    }

    @Test
    @DisplayName("Should return single user by ID")
    @Tag("Users") @Tag("Positive") @Tag("Smoke")
    void shouldReturnUserById() {
        int userId = ThreadLocalRandom.current().nextInt(1, 7);

        UserListItemResponse response = step("Make request", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                .when()
                        .get(SINGLE_USER, userId)
                .then()
                        .spec(ok200ResponseSpec())
                        .extract().jsonPath().getObject("data", UserListItemResponse.class)
        );

        step("Check that user is valid", () -> {
            assertThat(response.getId()).isEqualTo(userId);
            assertThat(response.getEmail()).contains("@");
        });
    }

    @Test
    @DisplayName("Should return 404 when user is not found")
    @Tag("Users") @Tag("Negative")
    void shouldReturn404WhenUserNotFound() {
        int userId = 99999999;

        Response response = step("Make request", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                .when()
                        .get(SINGLE_USER, userId)
        );

        step("Check that response status is 404", () ->
                response.then().spec(notFound404ResponseSpec())
        );
    }

    @Test
    @DisplayName("Should return 404 when ID is not numeric")
    @Tag("Users") @Tag("Negative")
    void shouldReturn404WhenIdIsNotNumeric() {
        String userId = "abc";

        Response response = step("Make request", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                .when()
                        .get(SINGLE_USER, userId)
        );

        step("Check that response status is 404", () ->
                response.then().spec(notFound404ResponseSpec())
        );
    }

    @Test
    @DisplayName("Should update user via PUT")
    @Tag("Users") @Tag("Positive")
    void shouldUpdateUserWithPut() {
        int userId = 2;
        UpdateUserRequestModel payload = new UpdateUserRequestModel("Tester", "QA Engineer");

        UpdateUserResponseModel response = step("Make request", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                        .body(payload)
                .when()
                        .put(SINGLE_USER, userId)
                .then()
                        .spec(ok200ResponseSpec())
                        .extract().as(UpdateUserResponseModel.class)
        );

        step("Check that user is updated", () -> {
            assertThat(response.getName()).isEqualTo("Tester");
            assertThat(response.getJob()).isEqualTo("QA Engineer");
            assertThat(response.getUpdatedAt()).isNotBlank();
        });
    }

    @Test
    @DisplayName("Should return 415 when Content-Type is missing")
    @Tag("Negative")
    void shouldReturn415WithoutContentType() {
        int userId = 2;
        UpdateUserRequestModel payload = new UpdateUserRequestModel("Tester", "QA Engineer");

        Response response = step("Make request", () ->
                given(requestWithoutContentType(baseUrl, apiKeyName, apiKeyValue))
                        .body(payload)
                .when()
                        .put(SINGLE_USER, userId)
        );

        step("Check that response status is 415", ()->
                response.then().spec(unsupportedMediaType415ResponseSpec())
        );
    }

    @Test
    @DisplayName("Should partially update user via PATCH")
    @Tag("Users") @Tag("Positive")
    void shouldPartiallyUpdateUserWithPatch() {
        int userId = 2;
        UpdateUserRequestModel payload = new UpdateUserRequestModel("", "QA Engineer");

        UpdateUserResponseModel response = step("Make request", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                        .body(payload)
                .when()
                        .put(SINGLE_USER, userId)
                .then()
                        .spec(ok200ResponseSpec())
                        .extract().as(UpdateUserResponseModel.class)
        );

        step("Check that user is updated", () -> {
            assertThat(response.getJob()).isEqualTo("QA Engineer");
            assertThat(response.getUpdatedAt()).isNotBlank();
        });
    }

    @Test
    @DisplayName("Should delete user successfully")
    @Tag("Users") @Tag("Positive") @Tag("Smoke")
    void shouldDeleteUser() {
        int userId = 2;

        Response response = step("Make request", () ->
                given(requestWithApiKey(baseUrl, apiKeyName, apiKeyValue))
                .when()
                        .delete(SINGLE_USER, userId)
        );

        step("Check that response status is 204", () ->
                response.then().spec(noContent204ResponseSpec())
        );
    }
}
