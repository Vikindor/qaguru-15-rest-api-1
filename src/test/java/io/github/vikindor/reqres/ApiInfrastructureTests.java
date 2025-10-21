package io.github.vikindor.reqres;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("API Infrastructure and Technical Validation Tests")
@Tag("Infrastructure") @Tag("Regression")
public class ApiInfrastructureTests extends TestBase{

    @Test
    @DisplayName("Should respond within acceptable time threshold")
    @Tag("Performance") @Tag("Smoke")
    void shouldHaveFastResponseTime() {
        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
        .when()
                .get("/users")
        .then()
                .statusCode(200)
                .time(lessThan(1500L));
    }

    @Test
    @DisplayName("Should return 304 when ETag matches (resource not modified)")
    @Tag("Etag")
    void shouldReturn304WhenResourceNotChanged() {
        String etag =
                given()
                        .baseUri(baseLink)
                        .header(apiKeyName, apiKeyValue)
                .when()
                        .get("/users")
                .then()
                        .statusCode(200)
                        .header("ETag", notNullValue())
                        .extract().header("ETag");

        given()
                .baseUri(baseLink)
                .header(apiKeyName, apiKeyValue)
        .when()
                .header("If-None-Match", etag)
                .get("/users")
        .then()
                .statusCode(304);
    }
}
