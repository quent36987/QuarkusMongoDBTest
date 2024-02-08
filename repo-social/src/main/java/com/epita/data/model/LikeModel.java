package com.epita.data.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @With
@MongoEntity(collection = "Likes")
public class LikeModel {
    @BsonId
    public ObjectId id;
    public String userId;
    public String postId;
    public boolean like;
    public LocalDateTime likeAt;
}
