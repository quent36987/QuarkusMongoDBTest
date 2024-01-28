package com.epita.repository;

import com.epita.model.LikeModel;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class LikeRepository implements PanacheMongoRepositoryBase<LikeModel, ObjectId> {

}
