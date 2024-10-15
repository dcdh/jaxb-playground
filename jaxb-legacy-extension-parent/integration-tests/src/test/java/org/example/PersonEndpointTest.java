package org.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PersonEndpointTest {

    @Test
    void shouldReturnPerson() {
        final String person = given()
                .when()
                .get("/person")
                .then()
                .log().all()
                .statusCode(200)
                .extract().asString();
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><person><age>41</age><name>Damien</name></person>", person);
    }
}
