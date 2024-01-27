package com.epita.service;

import com.epita.controller.RequestBody.CreatePostRequestBody;
import com.epita.model.PostModel;
import com.epita.repository.PostRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PostService {

    @Inject
    PostRepository postRepository;

    public List<PostModel> getAllPosts() {
        return postRepository.findAll().stream().toList();
    }

    public PostModel getPostById(String postId) {
        return postRepository.findById(postId);
    }

    public PostModel createPost(String userId, CreatePostRequestBody post) {
        PostModel postModel = new PostModel();
        postModel.userId = userId;
        postModel.text = post.getText();
        postModel.media = post.getMedia();
        postModel.createdAt = java.time.LocalDateTime.now().toString();
        postModel.replyPostId = null;
        postModel.repostId = null;

        postRepository.persist(postModel);

        return postModel;
    }

    public boolean deletePost(String userId, String postId) {
        PostModel post = postRepository.findById(postId);

        if (post == null || !post.userId.equals(userId)) {
            return false;
        }

        postRepository.deleteById(postId);

        return true;
    }
}

