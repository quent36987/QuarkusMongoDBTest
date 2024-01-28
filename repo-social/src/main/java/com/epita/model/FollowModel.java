package com.epita.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "Follows")
public class FollowModel {
    @BsonId
    public ObjectId id;
    public String userId;
    public String followId;
    public String followedAt;
}
