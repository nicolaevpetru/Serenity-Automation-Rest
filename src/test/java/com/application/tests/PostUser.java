package com.application.tests;

import com.application.pojo.User;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.*;

@ExtendWith(SerenityJUnit5Extension.class)
public class PostUser extends BaseClass {


    @DisplayName("POST /user")
    @Test
    public void testAddOneData() {
        User user1 = new User("New User", "Male", 394834L);

        SerenityRest.given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(user1)
                .when()
                .post("/user");

        Ensure.that("Status code is 201", v -> v.statusCode(201))
                .andThat("name is ", v -> v.body("data.name", is(user1.getName())));
    }
}
