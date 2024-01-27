package com.epita.controller;

import com.epita.service.PostService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/posts/{post_id}/reposts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RepostController {

    @Inject
    PostService postService;

    @POST
    public Response createRepost(@PathParam("post_id") ObjectId postId,
                                 @HeaderParam("X-user-id") String userId) {
        if (postId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return postService.createRepost(postId, userId);
    }
}