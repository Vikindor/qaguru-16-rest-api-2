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

    public static RequestSpecification requestWithApiKey(String baseLink, String apiKeyName, String apiKeyValue) {
        return with()
                .filter(withCustomTemplate())
                .log().uri()
                .log().body()
                .log().headers()
                .baseUri(baseLink)
                .accept(JSON)
                .contentType(JSON)
                .header(apiKeyName, apiKeyValue);
    }

    public static RequestSpecification requestWithoutContentType(String baseLink, String apiKeyName, String apiKeyValue) {
        return with()
                .filter(withCustomTemplate())
                .log().uri()
                .log().body()
                .log().headers()
                .baseUri(baseLink)
                .accept(JSON)
                .header(apiKeyName, apiKeyValue);
    }

    public static RequestSpecification requestWithoutApiKey(String baseLink) {
        return with()
                .filter(withCustomTemplate())
                .log().uri()
                .log().body()
                .log().headers()
                .baseUri(baseLink)
                .accept(JSON)
                .contentType(JSON);
    }

    public static ResponseSpecification unauthorized401ResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectContentType(JSON)
                .log(STATUS)
                .log(BODY)
                .build();
    }

    public static ResponseSpecification forbidden403ResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .expectContentType(JSON)
                .log(STATUS)
                .log(BODY)
                .build();
    }
}
