package com.epita.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity(collection = "Blocks")
public class BlockModel {
    @BsonId
    public ObjectId id;
    public String userId;
    public String blockId;
    public String blockAt;
}
