package com.epita.controller;

import com.epita.model.BlockModel;
import com.epita.service.BlockService;
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

    public static class Parameter {
        @RestHeader("X-user-id")
        String userId;
    }

    @GET
    @Path("/{user_id}/block-list")
    public Response getBlockedUsersByUserId(@PathParam("user_id") String userId) {
        if (userId == null || userId.isEmpty()) {
            return Response.status(400).build();
        }

        List<BlockModel> blockedUsers = blockService.getBlockedUsersByUserId(userId);

        return Response.ok(blockedUsers).build();
    }

    @POST
    @Path("/{user_id}/block")
    public Response blockUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId, String blockId) {
        if (parameter.userId == null || parameter.userId.isEmpty() || userId == null || userId.isEmpty() || blockId == null || blockId.isEmpty()) {
            return Response.status(404).build();
        }

        BlockModel block = blockService.blockUser(userId, blockId);

        return Response.ok(block).build();
    }

    @DELETE
    @Path("/{user_id}/block")
    public Response unblockUser(@BeanParam Parameter parameter, @PathParam("user_id") String userId, String blockId) {
        if (parameter.userId == null || parameter.userId.isEmpty() || userId == null || userId.isEmpty() || blockId == null || blockId.isEmpty()) {
            return Response.status(404).build();
        }

        boolean unblocked = blockService.unblockUser(userId, blockId);

        if (unblocked) {
            return Response.ok().build();
        } else {
            return Response.status(404).build();
        }
    }
}
