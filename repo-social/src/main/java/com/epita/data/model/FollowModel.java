package com.epita.data.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @With
@MongoEntity(collection = "Follows")
public class FollowModel {
    @BsonId
    public ObjectId id;
    public String userId;
    public String followId;
    public LocalDateTime followedAt;
}
