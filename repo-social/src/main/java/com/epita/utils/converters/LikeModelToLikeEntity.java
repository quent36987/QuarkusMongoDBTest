package com.epita.utils.converters;

import com.epita.data.model.LikeModel;
import com.epita.domain.entity.LikeEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LikeModelToLikeEntity {

    public LikeEntity convert(LikeModel likeModel) {
        return new LikeEntity(likeModel.id.toString(), likeModel.userId, likeModel.postId, likeModel.like, likeModel.likeAt);
    }

    public List<LikeEntity> convertList(List<LikeModel> models) {
        List<LikeEntity> responses = new ArrayList<>();
        for (LikeModel model : models) {
            responses.add(this.convert(model));
        }
        return responses;
    }
}
