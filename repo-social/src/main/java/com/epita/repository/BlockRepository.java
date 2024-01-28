package com.epita.repository;

import com.epita.model.BlockModel;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class BlockRepository implements PanacheMongoRepositoryBase<BlockModel, ObjectId> {

}
