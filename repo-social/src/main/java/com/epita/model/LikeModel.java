package com.epita.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "Likes")
public class LikeModel {
    @BsonId
    public ObjectId id;
    public String userId;
    public String postId;
    public boolean like;
    public String likeAt;
}
