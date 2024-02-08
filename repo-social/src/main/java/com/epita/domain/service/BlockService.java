package com.epita.domain.service;

import com.epita.data.model.BlockModel;
import com.epita.data.repository.BlockRepository;

import com.epita.domain.entity.BlockEntity;
import com.epita.utils.converters.BlockModelToBlockEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class BlockService {

    @Inject
    BlockRepository blockRepository;

    @Inject
    BlockModelToBlockEntity blockModelToBlockEntity;

    public List<BlockEntity> getBlockedUsersByUserId(String userId) {
        List<BlockModel> blocks = blockRepository.find("userId", userId).list();

        return blockModelToBlockEntity.convertList(blocks);
    }

    public List<BlockEntity> getBlockedByUsersByUserId(String userId) {
        List<BlockModel> blocks =  blockRepository.find("blockId", userId).list();

        return blockModelToBlockEntity.convertList(blocks);
    }

    public boolean isBlockedBy(String requestUserId, String destUserId) {
        BlockModel block = blockRepository.find("userId = ?1 and blockId = ?2", destUserId, requestUserId).firstResult();

        return block != null;
    }

    public BlockEntity blockUser(String userId, String blockId) {
        BlockModel blockModel = new BlockModel().withUserId(userId).withBlockId(blockId).withBlockAt(LocalDateTime.now());

        blockRepository.persist(blockModel);

        return blockModelToBlockEntity.convert(blockModel);
    }

    public boolean unblockUser(String userId, String blockId) {
        BlockModel blockModel = blockRepository.find("userId = ?1 and blockId = ?2", userId, blockId).firstResult();

        if (blockModel == null) {
            return false;
        }

        blockRepository.delete(blockModel);

        return true;
    }
}
