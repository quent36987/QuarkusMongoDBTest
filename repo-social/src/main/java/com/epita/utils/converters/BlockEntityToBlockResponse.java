package com.epita.utils.converters;

import com.epita.domain.entity.BlockEntity;
import com.epita.presentation.response.BlockResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BlockEntityToBlockResponse {

    public BlockResponse convert(BlockEntity blockEntity) {
        return new BlockResponse(blockEntity.id, blockEntity.userId, blockEntity.blockId, blockEntity.blockAt);
    }

    public List<BlockResponse> convertList(List<BlockEntity> entities) {
        List<BlockResponse> responses = new ArrayList<>();
        for (BlockEntity entity : entities) {
            responses.add(this.convert(entity));
        }
        return responses;
    }
}
