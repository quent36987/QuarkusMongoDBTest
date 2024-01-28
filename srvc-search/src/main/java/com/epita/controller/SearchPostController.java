package com.epita.controller;

import com.epita.model.PostModel;
import com.epita.service.SearchPostService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.List;

@Path("/api/posts/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchPostController {

    @Inject
    SearchPostService searchPostService;

    @GET
    public List<PostModel> searchPosts(@QueryParam("keywords") String keywords,
                                       @QueryParam("hashtags") List<String> hashtags) throws IOException {
        return searchPostService.searchPosts(keywords, hashtags);
    }
}
