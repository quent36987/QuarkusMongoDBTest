package com.epita.service;

import com.epita.model.ActionModel;
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
public class ActionSubscriber implements Consumer<ActionModel> {
    private final PubSubCommands.RedisSubscriber subscriber;

    @Inject
    ActionService actionService;
    public ActionSubscriber(final RedisDataSource ds) {
        subscriber = ds.pubsub(ActionModel.class).subscribe("add-action", this);
    }

    @Override
    public void accept(final ActionModel message) {
        try {
            actionService.storeAction(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}