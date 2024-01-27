package com.epita;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PostEndpointIntegrationTest {

    @Test
    public void testCreatePostEndpoint() {
        given()
                .header("X-user-id", "uuid")
                .when()
                .contentType("application/json")
                .body("{\"text\": \"Contenu du nouveau post.\", \"media\": \"http://example.com/media/image.jpg\"}")
                .post("/api/posts")
                .then()
                .statusCode(200)
                .body("text", is("Contenu du nouveau post."));
    }

//    @Test
//    public void testGetPostEndpoint() {
//        given()
//                .when()
//                .get("/api/posts/{post_id}", "post_id_value")
//                .then()
//                .statusCode(200)
//                .body("text", is("Ceci est le contenu du post."));
//    }
//
//    @Test
//    public void testDeletePostEndpoint() {
//        given()
//                .when()
//                .delete("/api/posts/{post_id}", "post_id_value")
//                .then()
//                .statusCode(204);
//    }
}
