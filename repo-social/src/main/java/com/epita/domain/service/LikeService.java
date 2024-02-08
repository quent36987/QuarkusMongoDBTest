package com.epita.domain.service;

import com.epita.data.model.LikeModel;
import com.epita.data.repository.LikeRepository;

import com.epita.domain.entity.LikeEntity;
import com.epita.utils.converters.LikeModelToLikeEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class LikeService {

    @Inject
    LikeRepository likeRepository;

    @Inject
    LikeModelToLikeEntity likeModelToLikeEntity;

    public List<LikeEntity> getAllLikesByPostId(String postId) {
        List<LikeModel> likes = likeRepository.find("postId", postId).list();

        return likeModelToLikeEntity.convertList(likes);
    }

    public LikeEntity likePost(String userId, String postId) {
        LikeModel like = new LikeModel().withPostId(postId).withUserId(userId).withLike(true).withLikeAt(LocalDateTime.now());

        likeRepository.persist(like);

        return likeModelToLikeEntity.convert(like);
    }

    public LikeEntity unlikePost(String userId, String postId) {
        // FIXME: Get post and set like to false ? Can not unlike an unliked post
        LikeModel like = new LikeModel().withPostId(postId).withUserId(userId).withLike(false).withLikeAt(LocalDateTime.now());

        likeRepository.persist(like);

        return likeModelToLikeEntity.convert(like);
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
