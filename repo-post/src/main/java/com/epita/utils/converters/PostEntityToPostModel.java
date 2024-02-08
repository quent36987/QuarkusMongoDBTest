package com.epita.utils.converters;

import com.epita.data.model.PostModel;
import com.epita.domain.entity.PostEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PostEntityToPostModel {

    public PostModel convert(PostEntity entity) {
        return new PostModel(
                new ObjectId(entity.id),
                entity.userId,
                entity.createdAt,
                entity.text,
                entity.media,
                entity.replyPostId,
                entity.repostId);
    }

    public List<PostModel> convertList(List<PostEntity> entities) {
        List<PostModel> responses = new ArrayList<>();
        for (PostEntity entity : entities) {
            responses.add(this.convert(entity));
        }
        return responses;
    }
}
