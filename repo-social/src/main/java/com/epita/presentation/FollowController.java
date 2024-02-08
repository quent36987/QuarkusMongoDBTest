package com.epita.presentation;

import com.epita.domain.entity.FollowEntity;
import com.epita.domain.service.BlockService;
import com.epita.domain.service.FollowService;
import com.epita.utils.converters.FollowEntityToFollowResponse;
import com.epita.utils.errors.RestError;
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

    @Inject
    FollowEntityToFollowResponse followEntityToFollowResponse;

    public static class Parameter {
        @RestHeader("X-user-id")
        String userId;
    }

    @GET
    @Path("/{user_id}/follows")
    public Response getFollowedUsersByUserId(@PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("userId");
        }

        List<FollowEntity> followedUsers = followService.getFollowedUsersByUserId(userId);

        return Response.ok(followEntityToFollowResponse.convertList(followedUsers)).build();
    }

    @GET
    @Path("/{user_id}/followers")
    public Response getFollowersByUserId(@PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("userId");
        }

        List<FollowEntity> followers = followService.getFollowersByUserId(userId);

        return Response.ok(followEntityToFollowResponse.convertList(followers)).build();
    }

    @POST
    @Path("/{user_id}/follow")
    public Response followUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("userId");
        }
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            throw RestError.MISSING_USER_ID.get();
        }

        if (blockService.isBlockedBy(parameter.userId, userId)) {
            throw RestError.FORBIDDEN.get(parameter.userId);
        }

        FollowEntity follow = followService.followUser(parameter.userId, userId);

        return Response.ok(followEntityToFollowResponse.convert(follow)).build();
    }

    @DELETE
    @Path("/{user_id}/follow")
    public Response unfollowUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("userId");
        }
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            throw RestError.MISSING_USER_ID.get();
        }

        boolean unfollowed = followService.unfollowUser(parameter.userId, userId);

        if (unfollowed) {
            return Response.ok().build();
        } else {
            throw RestError.USER_NOT_FOUND.get(userId);
        }
    }
}
