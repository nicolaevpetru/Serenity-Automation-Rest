package com.application.tests;


import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.*;

import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SerenityJUnit5Extension.class) //to get report otherwise it will run only Java program
//and will not create report
public class UserDataClass extends BaseClass {


    @DisplayName("Test user Can get single user Data")
    @Test
    public void test1user() {

        //get last valid ID from /users
        SerenityRest.given()
                .log().uri()
                .when()
                .get("/users");
        int lastId = lastResponse().path("id[-1]");
        System.out.println("lastId = " + lastId);

        SerenityRest.given()
                .pathParam("id", lastId)
                .when()
                .get("/users/{id}");

        /** Ensure.that("Verify Status code is 200",thenPart-> thenPart.statusCode(200));
         Ensure.that("Verify Content Type is JSON",contentType-> contentType.contentType(ContentType.JSON));
         */

        Ensure.that("Status code is 200", v -> v.statusCode(200))
                .andThat("Content Type is JSON", v -> v.contentType(ContentType.JSON))
                .andThat("The Id value is " + lastId
                        , v -> v.body("id", is(lastId)));

    }


    @Tag("ddt")
    @ParameterizedTest(name = "Testing user Data with ID of {0}")
    @ValueSource(ints = {5, 65, 44, 99, 115})//5,65,44,99,115
    //@ValueSource(ints = { 910, 911, 917, 918, 919}   )
    // use method source instead of magic number
    // get 10 data from the system and pass it here
    public void testOneUserDDT(int idParam) {

        //System.out.println("dParam = " + dParam);
        SerenityRest.given()
                .pathParam("id", idParam)
                .log().uri()
                .when()
                .get("/users/{id}");


        Ensure.that("Status code is 200", v -> v.statusCode(200))
                .andThat("Content Type is JSON", v -> v.contentType(ContentType.JSON))
                .andThat("The Id value is " + idParam
                        , v -> v.body("id", is(idParam)));

    }


    @Tag("MethodSource")
    @ParameterizedTest(name = "Testing User DDT with Method Source")
    @MethodSource("testMethodSource")
    public void testUserDDTWithMethodSource(int eachId) {
        SerenityRest.given()
                .pathParam("id", eachId)
                .log().uri()
                .when()
                .get("/user/{id}");

        Ensure.that("Status code is 200", v -> v.statusCode(200))
                .andThat("Content Type is JSON", v -> v.contentType(ContentType.JSON))
                .andThat("The Id value is " + eachId
                        , v -> v.body("id", is(eachId)));

    }


    public static List<Integer> testMethodSource() {
        List<Integer> listOfUsers = new ArrayList<>();
        listOfUsers.addAll(SerenityRest.get("/users").jsonPath().getList("id"));
        List<Integer> listOfIds = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            listOfIds.add(listOfUsers.get(new Faker().number().numberBetween(0, listOfUsers.size() - 1)));
        }
        return listOfIds;
    }


    @Tag("CSVSource")
    @ParameterizedTest
    @CsvFileSource(resources = "/UserInfo.csv", numLinesToSkip = 1)
    public void testUserDDTWithCSVSource(String eachName) {
        SerenityRest.given()
                .log().uri()
                .when()
                .get("/users");

        Ensure.that("Status code is 200", v -> v.statusCode(200))
                .andThat("Content Type is JSON", v -> v.contentType(ContentType.JSON))
                .andThat("The body contains " + eachName
                        , v -> v.body("name", hasItem(eachName)));
    }
}
