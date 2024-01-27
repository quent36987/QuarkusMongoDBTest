package com.epita.controller;

import com.epita.controller.RequestBody.CreatePostRequestBody;
import com.epita.model.PostModel;
import com.epita.service.PostService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.jboss.resteasy.reactive.RestHeader;

import java.util.List;

@Path("/api/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {

    @Inject
    PostService postService;

    public static class Parameter {
        @RestHeader("X-user-id")
        String userId;
    }

    @GET
    public List<PostModel> getAllPosts() {
        return postService.getAllPosts();
    }

    @GET
    @Path("/{post_id}")
    public Response getPostById(@PathParam("post_id") ObjectId postId) {
        if (postId == null ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        PostModel post = postService.getPostById(postId);

        if (post != null) {
            return Response.ok(post).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createPost(@BeanParam Parameter parameter, CreatePostRequestBody post) {
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            // FIXME verify if user exists
            return Response.status(400).build();
        }

        PostModel createdPost = postService.createPost(parameter.userId, post);

        if (createdPost == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(createdPost).build();
    }

    @DELETE
    @Path("/{post_id}")
    public Response deletePost(@BeanParam Parameter parameter, @PathParam("post_id") ObjectId postId) {
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            // FIXME verify if user exists
            return Response.status(400).build();
        }

        var isDelete = postService.deletePost(parameter.userId, postId);

        if (!isDelete) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();
    }
}

