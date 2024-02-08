package com.epita.presentation;

import com.epita.domain.entity.PostEntity;
import com.epita.presentation.request.CreateReplyRequestBody;
import com.epita.domain.service.PostService;
import com.epita.presentation.response.PostResponse;
import com.epita.utils.converters.PostEntityToPostResponse;
import com.epita.utils.errors.RestError;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/posts/{post_id}/replies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReplyController {

    @Inject
    PostService postService;

    @Inject
    PostEntityToPostResponse postEntityToPostReponse;

    @GET
    public Response getRepliesByPostId(@PathParam("post_id") String postId) {
        if (postId == null) {
            throw RestError.MISSING_FIELD.get("post_id");
        }

        if (!postService.existById(postId)) {
            throw RestError.POST_NOT_FOUND.get(postId);
        }

        List<PostResponse> posts = postEntityToPostReponse.convertList(postService.getRepliesByPostId(postId));

        return Response.ok(posts).build();
    }

    @POST
    public Response createReply(@PathParam("post_id") String postId, @HeaderParam("X-user-id") String userId, CreateReplyRequestBody reply) {
        if (postId == null) {
            throw RestError.MISSING_FIELD.get("post_id");
        }

        PostEntity post = postService.createReply(postId, userId, reply.media, reply.text);

        return Response.ok(postEntityToPostReponse.convert(post)).build();
    }
}
