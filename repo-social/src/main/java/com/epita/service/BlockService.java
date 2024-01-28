package com.epita.service;

import com.epita.model.BlockModel;
import com.epita.repository.BlockRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class BlockService {

    @Inject
    BlockRepository blockRepository;

    public List<BlockModel> getBlockedUsersByUserId(String userId) {
        return blockRepository.find("userId", userId).list();
    }

    public List<BlockModel> getBlockedByUsersByUserId(String userId) {
        return blockRepository.find("blockId", userId).list();
    }

    public boolean isBlockedBy(String requestUserId, String destUserId) {
        BlockModel block = blockRepository.find("userId = ?1 and blockId = ?2", destUserId, requestUserId).firstResult();

        if (block == null) {
            return false;
        }

        return true;
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
