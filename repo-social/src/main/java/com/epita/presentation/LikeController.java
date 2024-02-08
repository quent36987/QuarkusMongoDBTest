package com.epita.presentation;

import com.epita.domain.entity.ActionEntity;
import com.epita.domain.entity.LikeEntity;
import com.epita.domain.service.ActionPublisher;
import com.epita.domain.service.BlockService;
import com.epita.domain.service.LikeService;
import com.epita.utils.converters.LikeEntityToLikeResponse;
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
public class LikeController {

    @Inject
    LikeService likeService;

    @Inject
    BlockService blockService;

    @Inject
    ActionPublisher actionPublisher;

    @Inject
    LikeEntityToLikeResponse likeEntityToLikeResponse;

    public static class Parameter {
        @RestHeader("X-user-id")
        String userId;
    }

    @GET
    @Path("/{post_id}/likes")
    public Response getAllLikesByPostId(@PathParam("post_id") String postId) {
        if (postId == null || postId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("postId");
        }

        List<LikeEntity> likes = likeService.getAllLikesByPostId(postId);

        return Response.ok(likeEntityToLikeResponse.convertList(likes)).build();
    }

    @POST
    @Path("/{post_id}/like")
    public Response likePost(@BeanParam Parameter parameter, @PathParam("post_id") String postId) {
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            throw RestError.MISSING_USER_ID.get();
        }
        if (postId == null || postId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("postId");
        }

        LikeEntity like = likeService.likePost(parameter.userId, postId);

        //FIXME: Check if blocked user
        if (like != null) {
            actionPublisher.publishAction(new ActionEntity().withUserId(parameter.userId).withPostId(postId).withAction("like"));
            return Response.ok(likeEntityToLikeResponse.convert(like)).build();
        } else {
            throw RestError.POST_NOT_FOUND.get(postId);
        }
    }

    @POST
    @Path("/{post_id}/unlike")
    public Response unlikePost(@BeanParam Parameter parameter, @PathParam("post_id") String postId) {
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            throw RestError.MISSING_USER_ID.get();
        }
        if (postId == null || postId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("postId");
        }

        LikeEntity unlike = likeService.unlikePost(parameter.userId, postId);

        if (unlike != null) {
            actionPublisher.publishAction(new ActionEntity().withUserId(parameter.userId).withPostId(postId).withAction("unlike"));
            return Response.ok(likeEntityToLikeResponse.convert(unlike)).build();
        } else {
            throw RestError.POST_NOT_FOUND.get(postId);
        }
    }

    @DELETE
    @Path("/{post_id}/like")
    public Response unlikePostDelete(@BeanParam Parameter parameter, @PathParam("post_id") String postId) {
        return deleteLike(parameter, postId);
    }

    @DELETE
    @Path("/{post_id}/unlike")
    public Response unlikeUnlikePostDelete(@BeanParam Parameter parameter, @PathParam("post_id") String postId) {
        return deleteLike(parameter, postId);
    }

    private Response deleteLike(Parameter parameter, String postId) {
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            throw RestError.MISSING_USER_ID.get();
        }

        var unlikeUnlikeDelete = likeService.deletelikePost(parameter.userId, postId);

        if (unlikeUnlikeDelete) {
            actionPublisher.publishAction(new ActionEntity().withUserId(parameter.userId).withPostId(postId).withAction("deleteLike"));
            return Response.ok().build();
        } else {
            throw RestError.POST_NOT_FOUND.get(postId);
        }
    }
}
