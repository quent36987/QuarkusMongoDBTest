package com.epita.utils.converters;

import com.epita.data.model.LikeModel;
import com.epita.domain.entity.LikeEntity;
import com.epita.presentation.response.LikeResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LikeEntityToLikeResponse {

    public LikeResponse convert(LikeEntity likeEntity) {
        return new LikeResponse(likeEntity.id, likeEntity.userId, likeEntity.postId, likeEntity.like, likeEntity.likeAt);
    }

    public List<LikeResponse> convertList(List<LikeEntity> entities) {
        List<LikeResponse> responses = new ArrayList<>();
        for (LikeEntity entity : entities) {
            responses.add(this.convert(entity));
        }
        return responses;
    }
}
