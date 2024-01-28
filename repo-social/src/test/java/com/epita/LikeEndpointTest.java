package com.epita;

import com.epita.model.LikeModel;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class LikeEndpointTest {
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
    public void testLikePostEndpoint() {
        Response response = callPostEndpoint("/posts/1/like", X_USER_1, null);

        assertResponse(response, 200);

        LikeModel like = response.as(LikeModel.class);

        assertEquals(X_USER_1, like.userId);
        assertEquals("1", like.postId);
        Assertions.assertTrue(like.like);

        response = callGetEndpoint("/posts/1/likes", null, null);

        assertResponse(response, 200);

        LikeModel[] likes = response.as(LikeModel[].class);

        var likeFound = Arrays.stream(likes).filter(l -> l.id.equals(like.id)).findFirst();

        assertTrue(likeFound.isPresent());

        response = callDeleteEndpoint("/posts/1/like", X_USER_1, null);

        assertResponse(response, 200);

        response = callGetEndpoint("/posts/1/likes", null, null);

        assertResponse(response, 200);

        LikeModel[] likes2 = response.as(LikeModel[].class);

        var likeFound2 = Arrays.stream(likes2).filter(l -> l.id.equals(like.id)).findFirst();

        assertTrue(likeFound2.isEmpty());
    }
}
