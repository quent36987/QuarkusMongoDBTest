package com.epita.domain.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import com.epita.data.model.ActionsModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;

@ApplicationScoped
public class SearcActionService {

    @Inject
    ElasticsearchClient elasticsearchClient;

    public ActionsModel timeline(String userId) throws IOException {
        // get the document
        GetResponse<ActionsModel> actionsModel = elasticsearchClient.get(getRequest -> getRequest.index("actions").id(userId), ActionsModel.class);

        // if it exists, return it
        if (actionsModel.source() != null) {
            return actionsModel.source();
        } else {
            // if it doesn't exist, return an empty list
            return new ActionsModel();
        }
    }
}
