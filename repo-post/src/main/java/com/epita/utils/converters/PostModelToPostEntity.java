package com.epita.utils.converters;

import com.epita.data.model.PostModel;
import com.epita.domain.entity.PostEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PostModelToPostEntity {

    public PostEntity convert(PostModel postModel) {
        return new PostEntity(
                postModel.id.toString(), postModel.userId, postModel.createdAt, postModel.text, postModel.media, postModel.replyPostId, postModel.repostId);
    }

    public List<PostEntity> convertList(List<PostModel> models) {
        List<PostEntity> responses = new ArrayList<>();
        for (PostModel model : models) {
            responses.add(this.convert(model));
        }
        return responses;
    }
}
