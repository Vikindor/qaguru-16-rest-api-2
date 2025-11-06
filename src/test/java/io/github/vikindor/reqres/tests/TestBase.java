package io.github.vikindor.reqres.tests;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class TestBase {
    static String apiKeyName = "x-api-key";
    static String apiKeyValue = "reqres-free-v1";

    @BeforeAll
    static void setupConfig() {
        baseURI = "https://reqres.in";
        basePath = "/api";
    }
}
