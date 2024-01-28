package com.epita.repository;

import com.epita.model.FollowModel;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class FollowRepository implements PanacheMongoRepositoryBase<FollowModel, ObjectId> {

}
