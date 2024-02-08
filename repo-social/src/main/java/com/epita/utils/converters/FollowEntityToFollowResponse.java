package com.epita.utils.converters;

import com.epita.domain.entity.FollowEntity;
import com.epita.presentation.response.FollowResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FollowEntityToFollowResponse {

    public FollowResponse convert(FollowEntity followEntity) {
        return new FollowResponse(followEntity.id, followEntity.userId, followEntity.followId, followEntity.followedAt);
    }

    public List<FollowResponse> convertList(List<FollowEntity> entities) {
        List<FollowResponse> responses = new ArrayList<>();
        for (FollowEntity entity : entities) {
            responses.add(this.convert(entity));
        }
        return responses;
    }
}
