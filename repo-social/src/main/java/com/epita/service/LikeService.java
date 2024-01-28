package com.epita.service;

import com.epita.model.LikeModel;
import com.epita.repository.LikeRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class LikeService {

    @Inject
    LikeRepository likeRepository;

    public List<LikeModel> getAllLikesByPostId(String postId) {
        return likeRepository.find("postId", postId).list();
    }

    public LikeModel likePost(String userId,String postId) {
        LikeModel like = new LikeModel();
        like.postId = postId;
        like.userId = userId;
        like.like = true;
        like.likeAt = LocalDateTime.now().toString();

        likeRepository.persist(like);

        return like;
    }

    public LikeModel unlikePost(String userId,String postId) {
        LikeModel like = new LikeModel();
        like.postId = postId;
        like.userId = userId;
        like.like = false;
        like.likeAt = LocalDateTime.now().toString();

        likeRepository.persist(like);

        return like;
    }

    public boolean deletelikePost(String userId, String postId) {
        LikeModel like = likeRepository.find("postId = ?1 and userId = ?2", postId, userId).firstResult();

        if (like == null) {
            return false;
        }

        likeRepository.delete(like);

        return true;
    }
}
