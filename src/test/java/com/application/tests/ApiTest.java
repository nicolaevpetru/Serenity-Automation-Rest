package com.application.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityJUnit5Extension;

import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.application.utility.SerenityConfigReader.read;


@ExtendWith(SerenityJUnit5Extension.class)
public class ApiTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = read("qa.base.url");
        RestAssured.basePath = read("qa.base.path");
    }

    @AfterAll
    public static void tearDown() {
        RestAssured.reset();

    }


    @Tag("User")
    @DisplayName("Testing /dashboard_stats")
    @Test
    public void testDashboardStatus() {
        //get token using POST /login endpoint
        String myToken = SerenityRest.given()
                .log().all()
                .formParam("email", read("qa.email"))
                .formParam("password", read("qa.password"))
                .contentType(ContentType.URLENC)
                .when()
                .post("/login").path("token");

    }
}