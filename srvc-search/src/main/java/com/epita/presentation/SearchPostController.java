package com.epita.presentation;

import com.epita.data.model.PostModel;
import com.epita.domain.service.SearchPostService;
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

    // GET http://localhost:8083/api/posts/search?keywords=heureux%20en&hashtags=peu,très
    // recherche les posts contenant les mots "heureux" et "en" et obligatoirement "peu" et "très"
    @GET
    public List<PostModel> searchPosts(@QueryParam("keywords") String keywords, @QueryParam("hashtags") String hashtags) throws IOException {
        return searchPostService.searchPosts(keywords, hashtags);
    }
}
