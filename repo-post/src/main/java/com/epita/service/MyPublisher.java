package com.epita.service;

import com.epita.model.ActionModel;
import com.epita.model.PostRedisMessage;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyPublisher {
    private final PubSubCommands<PostRedisMessage> publisher;
    
    private final PubSubCommands<ActionModel> publisherAction;

    public MyPublisher(final RedisDataSource ds) {
        publisher = ds.pubsub(PostRedisMessage.class);
        publisherAction = ds.pubsub(ActionModel.class);
    }

    public void publishPost(final PostRedisMessage message) {
        publisher.publish("create-post", message);
    }

    public void publishDeletePost(final PostRedisMessage message) {
        publisher.publish("delete-post", message);
    }

    public void publishAction(final ActionModel message) {
        publisherAction.publish("add-action", message);
    }
}