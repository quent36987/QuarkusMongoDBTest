package com.epita.service;

import com.epita.model.PostRedisMessage;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyPublisher {
    private final PubSubCommands<PostRedisMessage> publisher;
    public MyPublisher(final RedisDataSource ds) {
        publisher = ds.pubsub(PostRedisMessage.class);
    }
    public void publishPost(final PostRedisMessage message) {
        publisher.publish("create-post", message);
    }

    public void publishDeletePost(final PostRedisMessage message) {
        publisher.publish("delete-post", message);
    }
}