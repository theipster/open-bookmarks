package com.github.theipster.openbookmarks.bookmarks;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class HttpRequest {

    private RequestSpecification spec;

    public HttpRequest() {
        spec = given();
    }

    public HttpRequest setBaseUri(String baseUri) {
        spec = spec.baseUri(baseUri);
        return this;
    }

    public HttpRequest setBody(String body) {
        spec = spec.body(body);
        return this;
    }

    public Response post(String path) {
        return spec.post(path);
    }
}
