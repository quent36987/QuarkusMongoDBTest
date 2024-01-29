package com.epita.controller;

import com.epita.model.ActionModel;
import com.epita.model.LikeModel;
import com.epita.service.ActionPublisher;
import com.epita.service.BlockService;
import com.epita.service.LikeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestHeader;

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

    public static class Parameter {
        @RestHeader("X-user-id")
        String userId;
    }


    @GET
    @Path("/{post_id}/likes")
    public Response getAllLikesByPostId(@PathParam("post_id") String postId) {
        if (postId == null || postId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(likeService.getAllLikesByPostId(postId)).build();
    }

    @POST
    @Path("/{post_id}/like")
    public Response likePost(@BeanParam Parameter parameter, @PathParam("post_id") String postId) {
        if (parameter.userId == null || parameter.userId.isEmpty() || postId == null || postId.isEmpty()) {
            // FIXME: vérifier si l'utilisateur existe
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (blockService.isBlockedBy(parameter.userId, postId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        LikeModel like = likeService.likePost(parameter.userId, postId);

        if (like != null) {
            actionPublisher.publishAction(new ActionModel(parameter.userId, postId, "like"));
            return Response.ok(like).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{post_id}/unlike")
    public Response unlikePost(@BeanParam Parameter parameter, @PathParam("post_id") String postId) {
        if (parameter.userId == null || parameter.userId.isEmpty() || postId == null || postId.isEmpty()) {
            // FIXME: vérifier si l'utilisateur existe
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (blockService.isBlockedBy(parameter.userId, postId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        LikeModel unlike =  likeService.unlikePost(parameter.userId, postId);

        if (unlike != null) {
            actionPublisher.publishAction(new ActionModel(parameter.userId, postId, "unlike"));
            return Response.ok(unlike).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
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
            // FIXME: vérifier si l'utilisateur existe
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var unlikeUnlikeDelete = likeService.deletelikePost(parameter.userId, postId);

        if (unlikeUnlikeDelete) {
            actionPublisher.publishAction(new ActionModel(parameter.userId, postId, "deleteLike"));
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
