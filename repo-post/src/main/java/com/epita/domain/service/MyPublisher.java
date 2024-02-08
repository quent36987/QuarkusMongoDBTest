package com.epita.domain.service;

import com.epita.domain.entity.ActionEntity;
import com.epita.domain.entity.PostMessageEntity;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyPublisher {
    private final PubSubCommands<PostMessageEntity> publisher;

    private final PubSubCommands<ActionEntity> publisherAction;

    public MyPublisher(final RedisDataSource ds) {
        publisher = ds.pubsub(PostMessageEntity.class);
        publisherAction = ds.pubsub(ActionEntity.class);
    }

    public void publishPost(final PostMessageEntity message) {
        publisher.publish("create-post", message);
    }

    public void publishDeletePost(final PostMessageEntity message) {
        publisher.publish("delete-post", message);
    }

    public void publishAction(final ActionEntity message) {
        publisherAction.publish("add-action", message);
    }
}