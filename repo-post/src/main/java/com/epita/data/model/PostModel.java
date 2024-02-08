package com.epita.data.model;

import java.time.LocalDateTime;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "Posts")
@AllArgsConstructor
@NoArgsConstructor
@With
@Getter
public class PostModel {
    @BsonId // FIXME: check if necessary
    public ObjectId id;
    public @NotNull String userId;
    public @NotNull LocalDateTime createdAt;
    public String text;
    public String media; // url's image/article
    public String replyPostId;
    public String repostId;
}
