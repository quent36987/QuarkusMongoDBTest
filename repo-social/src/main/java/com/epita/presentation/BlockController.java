package com.epita.presentation;

import com.epita.domain.entity.BlockEntity;
import com.epita.domain.service.BlockService;
import com.epita.utils.converters.BlockEntityToBlockResponse;
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
public class BlockController {

    @Inject
    BlockService blockService;

    @Inject
    BlockEntityToBlockResponse blockEntityToBlockResponse;

    public static class Parameter {
        @RestHeader("X-user-id")
        String userId;
    }

    @GET
    @Path("/{user_id}/block-list")
    public Response getBlockedUsersByUserId(@PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("userId");
        }

        List<BlockEntity> blockedUsers = blockService.getBlockedUsersByUserId(userId);

        return Response.ok(blockEntityToBlockResponse.convertList(blockedUsers)).build();
    }

    @GET
    @Path("/{user_id}/blocked-by")
    public Response getBlockedByUsersByUserId(@PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("userId");
        }

        List<BlockEntity> blockedByUsers = blockService.getBlockedByUsersByUserId(userId);

        return Response.ok(blockEntityToBlockResponse.convertList(blockedByUsers)).build();
    }

    @POST
    @Path("/{user_id}/block")
    public Response blockUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("userId");
        }
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            throw RestError.MISSING_USER_ID.get();
        }

        if (blockService.isBlockedBy(parameter.userId, userId)) {
            throw RestError.FORBIDDEN.get(parameter.userId);
        }

        BlockEntity block = blockService.blockUser(userId, parameter.userId);

        return Response.ok(blockEntityToBlockResponse.convert(block)).build();
    }

    @DELETE
    @Path("/{user_id}/block")
    public Response unblockUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            throw RestError.MISSING_FIELD.get("userId");
        }
        if (parameter.userId == null || parameter.userId.isEmpty()) {
            throw RestError.MISSING_USER_ID.get();
        }

        boolean unblocked = blockService.unblockUser(userId, userId);

        if (unblocked) {
            return Response.ok().build();
        } else {
            throw RestError.USER_NOT_FOUND.get(userId);
        }
    }
}
