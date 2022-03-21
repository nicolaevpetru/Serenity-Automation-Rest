package com.application.tests;

import static com.application.utility.SerenityConfigReader.read;
import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseClass {

    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = read("qa.base.url");
        RestAssured.basePath = "/api";

    }
    @AfterAll
    public static void teardown() {
        RestAssured.reset();
        //there is also a method to reset all rest-assured static fields
        //that serenity use Serenity.reset() method
        SerenityRest.rest();
    }
}