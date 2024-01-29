package com.epita.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.epita.model.ActionModel;
import com.epita.model.ActionsModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;

@ApplicationScoped
public class ActionService {

    @Inject
    ElasticsearchClient elasticsearchClient;

    public void createIndex() throws IOException {
        elasticsearchClient.indices().create(b -> b.index("actions"));
    }

    public void storeAction(ActionModel action) throws IOException {
        // try to get the document if it exists
        GetResponse<ActionsModel> actionsModel = elasticsearchClient.get(getRequest -> getRequest
                .index("actions")
                .id(action.userId), ActionsModel.class);

        // if it exists, add the new action to the list
        if (actionsModel.source() != null) {
            System.out.println("Action exists");
            ActionsModel actions = actionsModel.source();
            actions.actions.add(action);
            elasticsearchClient.index(indexRequest -> indexRequest
                    .index("actions")
                    .id(action.userId)
                    .document(actions));
        } else {
            System.out.println("Action doesn't exist");
            // if it doesn't exist, create a new document
            ActionsModel actions = new ActionsModel(action);
            IndexRequest<ActionsModel> request = IndexRequest.of(
                    b -> b.index("actions")
                            .id(action.userId)
                            .document(actions));

            elasticsearchClient.index(request);
       }
    }
}
