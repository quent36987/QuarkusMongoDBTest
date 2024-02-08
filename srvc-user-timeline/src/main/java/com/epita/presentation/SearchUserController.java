package com.epita.presentation;

import com.epita.data.model.ActionsModel;
import com.epita.domain.service.SearcActionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;

@Path("/api/user/{user_id}/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchUserController {

    @Inject
    SearcActionService searcActionService;

    @GET
    @Path("/timeline")
    public ActionsModel timeline(@PathParam("user_id") String user_id) throws IOException {
        return searcActionService.timeline(user_id);
    }
}
