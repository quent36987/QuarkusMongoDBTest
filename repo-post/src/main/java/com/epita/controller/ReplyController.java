package com.epita.controller;

import com.epita.controller.RequestBody.CreateReplyRequestBody;
import com.epita.service.PostService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/posts/{post_id}/replies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReplyController {

    @Inject
    PostService postService;

    @GET
    public Response getRepliesByPostId(@PathParam("post_id") ObjectId postId) {
        if (postId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (postService.getPostById(postId) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(postService.getRepliesByPostId(postId)).build();
    }

    @POST
    public Response createReply(@PathParam("post_id") ObjectId postId,
                                @HeaderParam("X-user-id") String userId,
                                CreateReplyRequestBody reply) {
        if (postId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return postService.createReply(postId, userId, reply);
    }
}
