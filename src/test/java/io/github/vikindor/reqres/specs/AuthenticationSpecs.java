package io.github.vikindor.reqres.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.github.vikindor.reqres.helpers.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class AuthenticationSpecs {

    public static RequestSpecification requestWithApiKey(String apiKeyName, String apiKeyValue) {
        return with()
                .filter(withCustomTemplate())
                .log().uri()
                .log().body()
                .log().headers()
                .accept(JSON)
                .contentType(JSON)
                .header(apiKeyName, apiKeyValue);
    }

    public static RequestSpecification requestWithoutContentType(String apiKeyName, String apiKeyValue) {
        return with()
                .filter(withCustomTemplate())
                .log().uri()
                .log().body()
                .log().headers()
                .accept(JSON)
                .header(apiKeyName, apiKeyValue);
    }

    public static RequestSpecification requestWithoutApiKey() {
        return with()
                .filter(withCustomTemplate())
                .log().uri()
                .log().body()
                .log().headers()
                .accept(JSON)
                .contentType(JSON);
    }
}
