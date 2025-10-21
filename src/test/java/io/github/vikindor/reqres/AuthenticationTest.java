package io.github.vikindor.reqres;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Authentication and Authorization Tests")
@Tag("Auth") @Tag("Regression")
public class AuthenticationTest extends TestBase{

    @Test
    @DisplayName("Should return 200 when API key is valid")
    @Tag("Auth") @Tag("Positive") @Tag("Smoke")
    void shouldReturn200WhenApiKeyValid() {
        int id = 1;

        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
        .when()
                .get("/users/{id}", id)
        .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Should return 403 when API key is invalid")
    @Tag("Auth") @Tag("Negative")
    void shouldReturn403WhenApiKeyInvalid() {
        int id = 1;

        given()
                .baseUri(baseLink)
                .header(apiKeyName, "invalid")
        .when()
                .get("/users/{id}", id)
        .then()
                .statusCode(403)
                .body("error", containsString("Invalid or inactive API key"));
    }

    @Test
    @DisplayName("Should return 401 when API key is missing")
    @Tag("Auth") @Tag("Negative")
    void shouldReturn401WhenApiKeyMissing() {
        int id = 1;

        given()
                .baseUri(baseLink)
        .when()
                .get("/users/{id}", id)
        .then()
                .statusCode(401)
                .body("error", containsString("Missing API key"));
    }
}
