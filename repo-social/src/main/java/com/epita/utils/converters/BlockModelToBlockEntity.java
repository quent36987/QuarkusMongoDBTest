package com.epita.utils.converters;

import com.epita.data.model.BlockModel;
import com.epita.domain.entity.BlockEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BlockModelToBlockEntity {

    public BlockEntity convert(BlockModel blockModel) {
        return new BlockEntity(blockModel.id.toString(), blockModel.userId, blockModel.blockId, blockModel.blockAt);
    }

    public List<BlockEntity> convertList(List<BlockModel> models) {
        List<BlockEntity> responses = new ArrayList<>();
        for (BlockModel model : models) {
            responses.add(this.convert(model));
        }
        return responses;
    }
}
