package com.epita.controller;

import com.epita.model.FollowModel;

import com.epita.model.FollowModel;
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

    @POST
    @Path("/{user_id}/follow")
    public Response followUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId, String followId) {
        if (parameter.userId == null || parameter.userId.isEmpty() || userId == null || userId.isEmpty() || followId == null || followId.isEmpty()) {
            return Response.status(404).build();
        }

        FollowModel follow = followService.followUser(userId, followId);

        return Response.ok(follow).build();
    }

    @DELETE
    @Path("/{user_id}/follow")
    public Response unfollowUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId, String followId) {
        if (parameter.userId == null || parameter.userId.isEmpty() || userId == null || userId.isEmpty() || followId == null || followId.isEmpty()) {
            return Response.status(404).build();
        }

        boolean unfollowed = followService.unfollowUser(userId, followId);

        if (unfollowed) {
            return Response.ok().build();
        } else {
            return Response.status(404).build();
        }
    }
}
