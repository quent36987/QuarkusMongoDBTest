package com.epita.service;

import com.epita.model.BlockModel;
import com.epita.repository.BlockRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class BlockService {

    @Inject
    BlockRepository blockRepository;

    public List<BlockModel> getBlockedUsersByUserId(String userId) {
        return blockRepository.find("userId", userId).list();
    }

    public BlockModel blockUser(String userId, String blockId) {
        BlockModel block = new BlockModel();
        block.userId = userId;
        block.blockId = blockId;
        block.blockAt = LocalDateTime.now().toString();

        blockRepository.persist(block);

        return block;
    }

    public boolean unblockUser(String userId, String blockId) {
        BlockModel block = blockRepository.find("userId = ?1 and blockId = ?2", userId, blockId).firstResult();

        if (block == null) {
            return false;
        }

        blockRepository.delete(block);

        return true;
    }
}
