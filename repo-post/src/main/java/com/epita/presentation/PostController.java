package com.epita.presentation;

import com.epita.domain.entity.PostEntity;
import com.epita.presentation.request.CreatePostRequest;
import com.epita.domain.entity.ActionEntity;
import com.epita.domain.entity.PostMessageEntity;
import com.epita.domain.service.MyPublisher;
import com.epita.domain.service.PostService;
import com.epita.utils.converters.PostEntityToPostResponse;
import com.epita.utils.errors.RestError;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestHeader;
import java.util.List;

@Path("/api/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {

    @Inject
    PostService postService;

    @Inject
    MyPublisher myPublisher;

    @Inject
    PostEntityToPostResponse postEntityToPostResponse;

    public static class Parameter {
        @RestHeader("X-user-id")
        String userId;
    }

    @GET
    public Response getAllPosts() {
        List<PostEntity> posts = postService.getAllPosts();

        return Response.ok(postEntityToPostResponse.convertList(posts)).build();
    }

    @GET
    @Path("/{post_id}")
    public Response getPostById(@PathParam("post_id") String postId) {
        if (postId == null) {
            throw RestError.MISSING_FIELD.get("post_id");
        }

        PostEntity post = postService.getPostById(postId);

        return Response.ok(postEntityToPostResponse.convert(post)).build();
    }

    @POST
    public Response createPost(@BeanParam Parameter parameter, CreatePostRequest post) {
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            throw RestError.MISSING_USER_ID.get(); // FIXME verify if user exists
        }

        PostEntity createdPost = postService.createPost(parameter.userId, post.text, post.media);

        if (createdPost == null) {
            throw RestError.BAD_REQUEST.get();
        }

        PostMessageEntity postRedisMessage = new PostMessageEntity(createdPost.id, createdPost.text, createdPost.media);
        myPublisher.publishPost(postRedisMessage);

        myPublisher.publishAction(new ActionEntity().withPostId(createdPost.id).withAction("createPost").withUserId(parameter.userId));

        return Response.ok(postEntityToPostResponse.convert(createdPost)).build();
    }

    @DELETE
    @Path("/{post_id}")
    public Response deletePost(@BeanParam Parameter parameter, @PathParam("post_id") String postId) {
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            throw RestError.MISSING_USER_ID.get(); // FIXME verify if user exists
        }

        boolean isDelete = postService.deletePost(parameter.userId, postId);

        if (!isDelete) {
            throw RestError.BAD_REQUEST.get();
        }

        myPublisher.publishDeletePost(new PostMessageEntity(postId, null, null));
        myPublisher.publishAction(new ActionEntity().withPostId(postId).withAction("deletePost").withUserId(parameter.userId));

        return Response.ok().build();
    }
}
