package com.epita.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.epita.model.ActionModel;
import com.epita.model.ActionsModel;
import com.epita.model.PostModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SearcActionService {

    @Inject
    ElasticsearchClient elasticsearchClient;

    public ActionsModel timeline(String userId) throws IOException {
        // get the document
        GetResponse<ActionsModel> actionsModel = elasticsearchClient.get(getRequest -> getRequest
                .index("actions")
                .id(userId), ActionsModel.class);

        // if it exists, return it
        if (actionsModel.source() != null) {
            return  actionsModel.source();
        } else {
            // if it doesn't exist, return an empty list
            return new ActionsModel();
        }
    }
}
