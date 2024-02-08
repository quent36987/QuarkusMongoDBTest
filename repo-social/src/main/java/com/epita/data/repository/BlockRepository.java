package com.epita.data.repository;

import com.epita.data.model.BlockModel;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class BlockRepository implements PanacheMongoRepositoryBase<BlockModel, ObjectId> {

}
