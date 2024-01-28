package com.epita;

import com.epita.model.LikeModel;
import com.epita.repository.LikeRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class LikeEndpointTest {
    public static final String X_USER_HEADER_NAME = "X-user-id";
    private static final String X_USER_1 = "michel";
    private static final String X_USER_2 = "claude";

    @Test
    public void testLikePostEndpoint() {
        String postId = "your_post_id"; // replace with an actual post ID

        Response response = given()
                .header("Content-Type", ContentType.JSON)
                .header("X-user-id", "user_id") // replace with an actual user ID
                .post("/api/posts/" + postId + "/like")
                .then()
                .statusCode(200)
                .extract()
                .response();

        LikeModel like = response.as(LikeModel.class);
        // Assert that the like object contains the correct data
        // You can add more assertions based on your requirements
        // For example:
        // assertEquals("your_expected_user_id", like.userId);
        // assertEquals("your_expected_post_id", like.postId);
        // assertNotNull(like.likeAt);

        // Clean up by deleting the like
    }
}
