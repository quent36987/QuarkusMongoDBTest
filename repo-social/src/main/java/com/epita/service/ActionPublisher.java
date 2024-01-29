package com.epita.service;

import com.epita.model.ActionModel;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActionPublisher {

    private final PubSubCommands<ActionModel> publisherAction;

    public ActionPublisher(final RedisDataSource ds) {
        publisherAction = ds.pubsub(ActionModel.class);
    }

    public void publishAction(final ActionModel message) {
        publisherAction.publish("add-action", message);
    }
}