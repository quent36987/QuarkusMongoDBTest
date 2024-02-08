package com.epita.domain.service;

import com.epita.data.model.PostModel;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.function.Consumer;

@Startup
@ApplicationScoped
public class CreatePostSubscriber implements Consumer<PostModel> {
    private final PubSubCommands.RedisSubscriber subscriber;

    @Inject
    PostService postService;

    public CreatePostSubscriber(final RedisDataSource ds) {
        subscriber = ds.pubsub(PostModel.class).subscribe("create-post", this);
    }

    @Override
    public void accept(final PostModel message) {
        try {
            postService.storePost(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}