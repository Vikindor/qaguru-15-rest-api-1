package io.github.vikindor.reqres;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Users Endpoint Tests")
@Tag("Users") @Tag("Regression")
public class UsersTests extends TestBase {

    @Test
    @DisplayName("Should validate basic user fields")
    @Tag("Users") @Tag("Positive") @Tag("Smoke")
    void shouldValidateBasicFieldsForUser() {
        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
        .when()
                .get("/users")
        .then()
                .statusCode(200)
                .body("data.id", everyItem(notNullValue()))
                .body("data.email", everyItem(matchesPattern(".+@.+\\..+")))
                .body("data.first_name", everyItem(notNullValue()))
                .body("data.last_name", everyItem(notNullValue()))
                .body("data.avatar", everyItem(notNullValue()));
    }

    @Test
    @DisplayName("Should return empty list when page is beyond total")
    @Tag("Pagination") @Tag("Positive")
    void shouldReturnEmptyListWhenPageBeyondTotal() {
        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
        .when()
                .get("/users?page=9999")
        .then()
                .statusCode(200)
                .body("data.size()", equalTo(0));
    }

    @Test
    @DisplayName("Should return single user by ID")
    @Tag("Users") @Tag("Positive") @Tag("Smoke")
    void shouldReturnUserById() {
        int id = ThreadLocalRandom.current().nextInt(1,7);

        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
        .when()
                .get("/users/{id}", id)
        .then()
                .statusCode(200)
                .body("data.id", equalTo(id))
                .body("data.email", containsString("@"));
    }

    @Test
    @DisplayName("Should return 404 when user is not found")
    @Tag("Users") @Tag("Negative")
    void shouldReturn404WhenUserNotFound() {
        int id = 99999999;

        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
        .when()
                .get("/users/{id}", id)
        .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Should return 404 when ID is not numeric")
    @Tag("Users") @Tag("Negative")
    void shouldReturn404WhenIdIsNotNumeric() {
        String id = "abc";

        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
        .when()
                .get("/users/{id}", id)
        .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Should update user via PUT")
    @Tag("Users") @Tag("Positive")
    void shouldUpdateUserWithPut() {
        int id = 2;
        String body = """
                { "name": "Tester", "job": "QA Engineer" }
                """;

        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
                .contentType("application/json")
                .body(body)
        .when()
                .put("users/{id}", id)
        .then()
                .statusCode(200)
                .body("name", equalTo("Tester"))
                .body("job", equalTo("QA Engineer"))
                .body("updatedAt", notNullValue());
    }

    @Test
    @DisplayName("Should return 415 when Content-Type is missing")
    @Tag("Negative")
    void shouldReturn415WithoutContentType() {
        int id = 2;
        String body = """
                { "name": "Tester", "job": "QA Engineer" }
                """;

        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
                .body(body)
        .when()
                .put("users/{id}", id)
        .then()
                .statusCode(415);
    }

    @Test
    @DisplayName("Should partially update user via PATCH")
    @Tag("Users") @Tag("Positive")
    void shouldPartiallyUpdateUserWithPatch() {
        int id = 2;
        String body = """
                { "job": "QA Engineer" }
                """;

        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
                .contentType("application/json")
                .body(body)
        .when()
                .patch("users/{id}", id)
        .then()
                .statusCode(200)
                .body("job", equalTo("QA Engineer"))
                .body("updatedAt", notNullValue());
    }

    @Test
    @DisplayName("Should delete user successfully")
    @Tag("Users") @Tag("Positive") @Tag("Smoke")
    void shouldDeleteUser() {
        int id = 2;

        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
        .when()
                .delete("users/{id}", id)
        .then()
                .statusCode(204);
    }
}
