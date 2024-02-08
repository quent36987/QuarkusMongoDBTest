package com.epita.domain.service;

import com.epita.data.model.FollowModel;
import com.epita.data.repository.FollowRepository;

import com.epita.domain.entity.FollowEntity;
import com.epita.utils.converters.FollowModelToFollowEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class FollowService {

    @Inject
    FollowRepository followRepository;

    @Inject
    FollowModelToFollowEntity followModelToFollowEntity;

    public List<FollowEntity> getFollowedUsersByUserId(String userId) {
        List<FollowModel> follows = followRepository.find("userId", userId).list();

        return followModelToFollowEntity.convertList(follows);
    }

    public List<FollowEntity> getFollowersByUserId(String userId) {
        List<FollowModel> follows =  followRepository.find("followId", userId).list();

        return followModelToFollowEntity.convertList(follows);
    }

    public FollowEntity followUser(String userId, String followId) {
        FollowModel follow = new FollowModel().withUserId(userId).withFollowId(followId).withFollowedAt(LocalDateTime.now());

        followRepository.persist(follow);

        return followModelToFollowEntity.convert(follow);
    }

    public boolean unfollowUser(String userId, String followId) {
        FollowModel followModel = followRepository.find("userId = ?1 and followId = ?2", userId, followId).firstResult();

        if (followModel == null) {
            return false;
        }

        followRepository.delete(followModel);

        return true;
    }
}
