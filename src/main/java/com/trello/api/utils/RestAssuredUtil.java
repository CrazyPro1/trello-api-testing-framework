package com.trello.api.utils;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredUtil {

    private static RequestSpecification getRequestSpecification() {
        return RestAssured.given()
                .baseUri(ConfigReader.getProperty("base.url"))
                .queryParam("key", ConfigReader.getProperty("trello.key"))
                .queryParam("token", ConfigReader.getProperty("trello.token"))
                .contentType("application/json");
    }

    public static Response get(String path) {
        return getRequestSpecification()
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public static Response post(String path, Object payload) {
        return getRequestSpecification()
                .body(payload)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    public static Response put(String path, Object payload) {
        return getRequestSpecification()
                .body(payload)
                .when()
                .put(path)
                .then()
                .extract()
                .response();
    }

    public static Response delete(String path) {
        return getRequestSpecification()
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
    }
}