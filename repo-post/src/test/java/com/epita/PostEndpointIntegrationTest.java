package com.epita;

import com.epita.controller.RequestBody.CreatePostRequestBody;
import com.epita.model.PostModel;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PostEndpointIntegrationTest {

    public static final String X_USER_HEADER_NAME = "X-user-id";
    private static final String X_USER_1 = "michel";
    private static final String X_USER_2 = "claude";

    protected Response callPostEndpoint(String endpoint, String tenant, Object body) {
        RequestSpecification request = RestAssured.given().header("Content-Type", "application/json");

        if (tenant != null) {
            request.header(X_USER_HEADER_NAME, tenant);
        }

        if (body != null) {
            request.body(body);
        }

        return request.post("/api" + endpoint).andReturn();
    }

    protected Response callGetEndpoint(String endpoint, String tenant, Object body) {
        RequestSpecification request = RestAssured.given().header("Content-Type", "application/json");

        if (tenant != null) {
            request.header(X_USER_HEADER_NAME, tenant);
        }

        if (body != null) {
            request.body(body);
        }

        return request.get("/api" + endpoint).andReturn();
    }

    protected Response callDeleteEndpoint(String endpoint, String tenant, Object body) {
        RequestSpecification request = RestAssured.given().header("Content-Type", "application/json");

        if (tenant != null) {
            request.header(X_USER_HEADER_NAME, tenant);
        }

        if (body != null) {
            request.body(body);
        }

        return request.delete("/api" + endpoint).andReturn();
    }


    private void assertResponse(Response response, Integer expectedStatusCode) {
        assertResponse(response, expectedStatusCode, null);
    }

    private void assertResponse(Response response, Integer expectedStatusCode, Object expectedResponseBody) {
        assertEquals(expectedStatusCode, response.statusCode());
        if (Objects.nonNull(expectedResponseBody)) {
            assertTrue(EqualsBuilder.reflectionEquals(response.as(expectedResponseBody.getClass()), expectedResponseBody));
        }
    }

    @Test
    public void testCreatePostEndpoint() {
        CreatePostRequestBody createPostRequestBody = new CreatePostRequestBody("Ceci est mon post !",null);
        Response reponse = callPostEndpoint("/posts", X_USER_1, createPostRequestBody);

        assertEquals(200,reponse.statusCode(), "Status code should be 200, got " + reponse.statusCode());

        var post = reponse.as(PostModel.class);

        assertEquals(createPostRequestBody.getText(), post.text, "Post text should be the same, got " + post.text);
        assertEquals(createPostRequestBody.getMedia(), post.media, "Post media should be the same, got " + post.media);
        assertEquals(X_USER_1, post.userId, "Post userId should be the same, got " + post.userId);

        reponse = callGetEndpoint("/posts/" + post.id.toString(), X_USER_1, null);

        assertEquals(200,reponse.statusCode(),"Status code should be 200, got " + reponse.statusCode());

        var post2 = reponse.as(PostModel.class);

        assertEquals(post.id, post2.id, "Post id should be the same");

        reponse = callDeleteEndpoint("/posts/" + post.id.toString(), X_USER_1, null);

        assertEquals(200,reponse.statusCode(),"Status code should be 200, got " + reponse.statusCode());

        reponse = callGetEndpoint("/posts/" + post.id.toString(), X_USER_1, null);

        assertEquals(404,reponse.statusCode(),"Status code should be 404, got " + reponse.statusCode());
    }

    @Test
    public void testCreatePostEndpointWithoutUserId() {
        CreatePostRequestBody createPostRequestBody = new CreatePostRequestBody("Ce post ne devrait pas etre crée",null);
        Response reponse = callPostEndpoint("/posts", null, createPostRequestBody);

        assertEquals(400,reponse.statusCode(), "Status code should be 400, got " + reponse.statusCode());
    }

    @Test
    public void testDeleteNotMine() {
        CreatePostRequestBody createPostRequestBody = new CreatePostRequestBody("Ceci est mon post !",null);
        Response reponse = callPostEndpoint("/posts", X_USER_1, createPostRequestBody);

        assertEquals(200,reponse.statusCode(), "Status code should be 200, got " + reponse.statusCode());

        var post = reponse.as(PostModel.class);

        assertEquals(createPostRequestBody.getText(), post.text, "Post text should be the same, got " + post.text);
        assertEquals(createPostRequestBody.getMedia(), post.media, "Post media should be the same, got " + post.media);
        assertEquals(X_USER_1, post.userId, "Post userId should be the same, got " + post.userId);

        reponse = callGetEndpoint("/posts/" + post.id.toString(), X_USER_1, null);

        assertEquals(200,reponse.statusCode(),"Status code should be 200, got " + reponse.statusCode());

        var post2 = reponse.as(PostModel.class);

        assertEquals(post.id, post2.id, "Post id should be the same");

        reponse = callDeleteEndpoint("/posts/" + post.id.toString(), X_USER_2, null);

        assertEquals(400,reponse.statusCode(),"Status code should be 400, got " + reponse.statusCode());

        reponse = callDeleteEndpoint("/posts/" + post.id.toString(), X_USER_1, null);

        assertEquals(200,reponse.statusCode(),"Status code should be 200, got " + reponse.statusCode());

        reponse = callGetEndpoint("/posts/" + post.id.toString(), X_USER_1, null);

        assertEquals(404,reponse.statusCode(),"Status code should be 404, got " + reponse.statusCode());
    }
}
