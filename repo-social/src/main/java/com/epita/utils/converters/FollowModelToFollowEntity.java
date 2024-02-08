package com.epita.utils.converters;

import com.epita.data.model.FollowModel;
import com.epita.domain.entity.FollowEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FollowModelToFollowEntity {

    public FollowEntity convert(FollowModel followModel) {
        return new FollowEntity(followModel.id.toString(), followModel.userId, followModel.followId, followModel.followedAt);
    }

    public List<FollowEntity> convertList(List<FollowModel> models) {
        List<FollowEntity> responses = new ArrayList<>();
        for (FollowModel model : models) {
            responses.add(this.convert(model));
        }
        return responses;
    }
}
