package com.epita.data.repository;

import com.epita.data.model.PostModel;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class PostRepository implements PanacheMongoRepositoryBase<PostModel, ObjectId> {

}
