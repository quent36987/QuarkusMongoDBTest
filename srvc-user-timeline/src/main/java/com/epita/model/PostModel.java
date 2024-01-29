package com.epita.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "Posts")
public class PostModel {
    @BsonId
    public ObjectId id;
    public String text;
    public String media;
    public String replyPostId;
    public String createdAt;
    public String userId;
    public String repostId;
}

