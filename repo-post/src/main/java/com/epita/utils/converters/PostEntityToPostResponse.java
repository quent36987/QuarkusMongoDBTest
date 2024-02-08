package com.epita.utils.converters;

import com.epita.domain.entity.PostEntity;
import com.epita.presentation.response.PostResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PostEntityToPostResponse {
    public PostResponse convert(PostEntity entity) {
        return new PostResponse(entity.id, entity.userId, entity.createdAt, entity.text, entity.media, entity.replyPostId, entity.repostId);
    }

    public List<PostResponse> convertList(List<PostEntity> entities) {
        List<PostResponse> responses = new ArrayList<>();
        for (PostEntity entity : entities) {
            responses.add(this.convert(entity));
        }
        return responses;
    }
}
