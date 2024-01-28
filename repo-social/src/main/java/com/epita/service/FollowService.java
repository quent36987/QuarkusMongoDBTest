package com.epita.service;

import com.epita.model.FollowModel;
import com.epita.repository.FollowRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class FollowService {

    @Inject
    FollowRepository followRepository;

    public List<FollowModel> getFollowedUsersByUserId(String userId) {
        return followRepository.find("userId", userId).list();
    }

    public List<FollowModel> getFollowersByUserId(String userId) {
        return followRepository.find("followId", userId).list();
    }

    public FollowModel followUser(String userId, String followId) {
        FollowModel follow = new FollowModel();
        follow.userId = userId;
        follow.followId = followId;
        follow.followedAt = LocalDateTime.now().toString();

        followRepository.persist(follow);

        return follow;
    }

    public boolean unfollowUser(String userId, String followId) {
        FollowModel follow = followRepository.find("userId = ?1 and followId = ?2", userId, followId).firstResult();

        if (follow == null) {
            return false;
        }

        followRepository.delete(follow);

        return true;
    }
}
