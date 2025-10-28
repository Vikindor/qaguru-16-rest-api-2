package io.github.vikindor.reqres.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class ApiInfrastructureSpecs {

    public static ResponseSpecification ok200ResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(STATUS)
                .log(BODY)
                .build();
    }

    public static ResponseSpecification noContent204ResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .log(STATUS)
                .log(BODY)
                .build();
    }

    public static ResponseSpecification notModified304ResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(304)
                .log(STATUS)
                .log(BODY)
                .build();
    }

    public static ResponseSpecification notFound404ResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .log(STATUS)
                .log(BODY)
                .build();
    }

    public static ResponseSpecification unsupportedMediaType415ResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(415)
                .log(STATUS)
                .log(BODY)
                .build();
    }
}
