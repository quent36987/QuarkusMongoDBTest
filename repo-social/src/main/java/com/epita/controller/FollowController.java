package com.epita.controller;

import com.epita.model.FollowModel;

import com.epita.service.BlockService;
import com.epita.service.FollowService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestHeader;

import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FollowController {

    @Inject
    FollowService followService;

    @Inject
    BlockService blockService;

    public static class Parameter {
        @RestHeader("X-user-id")
        String userId;
    }

    @GET
    @Path("/{user_id}/follows")
    public Response getFollowedUsersByUserId(@PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(400).build();
        }

        List<FollowModel> followedUsers = followService.getFollowedUsersByUserId(userId);

        return Response.ok(followedUsers).build();
    }

    @GET
    @Path("/{user_id}/followers")
    public Response getFollowersByUserId(@PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(400).build();
        }

        List<FollowModel> followers = followService.getFollowersByUserId(userId);

        return Response.ok(followers).build();
    }

    @POST
    @Path("/{user_id}/follow")
    public Response followUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId) {
        if (parameter.userId == null || parameter.userId.isEmpty() || userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (blockService.isBlockedBy(parameter.userId, userId)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        FollowModel follow = followService.followUser(parameter.userId, userId);

        return Response.ok(follow).build();
    }

    @DELETE
    @Path("/{user_id}/follow")
    public Response unfollowUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId) {
        if (parameter.userId == null || parameter.userId.isEmpty() || userId == null || userId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        boolean unfollowed = followService.unfollowUser(parameter.userId, userId);

        if (unfollowed) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
