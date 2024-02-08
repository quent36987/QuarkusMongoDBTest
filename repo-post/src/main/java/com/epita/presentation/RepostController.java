package com.epita.presentation;

import com.epita.domain.entity.PostEntity;
import com.epita.domain.service.PostService;
import com.epita.utils.converters.PostEntityToPostResponse;
import com.epita.utils.errors.RestError;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/posts/{post_id}/reposts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RepostController {

    @Inject
    PostService postService;

    @Inject
    PostEntityToPostResponse postEntityToPostResponse;

    @POST
    public Response createRepost(@PathParam("post_id") String postId, @HeaderParam("X-user-id") String userId) {
        if (postId == null) {
            throw RestError.MISSING_FIELD.get("post_id");
        }

        PostEntity post = postService.createRepost(userId, postId);

        return Response.ok(postEntityToPostResponse.convert(post)).build();
    }
}