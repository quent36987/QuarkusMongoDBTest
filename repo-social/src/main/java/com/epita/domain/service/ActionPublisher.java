package com.epita.domain.service;

import com.epita.domain.entity.ActionEntity;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActionPublisher {

    private final PubSubCommands<ActionEntity> publisherAction;

    public ActionPublisher(final RedisDataSource ds) {
        publisherAction = ds.pubsub(ActionEntity.class);
    }

    public void publishAction(final ActionEntity message) {
        publisherAction.publish("add-action", message);
    }
}