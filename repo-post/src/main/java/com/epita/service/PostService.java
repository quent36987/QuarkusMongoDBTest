package com.epita.service;

import com.epita.controller.RequestBody.CreatePostRequestBody;
import com.epita.controller.RequestBody.CreateReplyRequestBody;
import com.epita.model.PostModel;
import com.epita.repository.PostRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class PostService {

    @Inject
    PostRepository postRepository;

    public List<PostModel> getAllPosts() {
        return postRepository.findAll().stream().toList();
    }

    public PostModel getPostById(ObjectId postId) {
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

    public boolean deletePost(String userId, ObjectId postId) {
        PostModel post = postRepository.findById(postId);

        if (post == null || !post.userId.equals(userId)) {
            return false;
        }

        return postRepository.deleteById(postId);
    }

    public List<PostModel> getRepliesByPostId(ObjectId postId) {
        return postRepository.find("replyPostId", postId).list();
    }

    public Response createReply(ObjectId postId, String userId, CreateReplyRequestBody reply) {
        PostModel post = postRepository.findById(postId);

        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PostModel replyPost = new PostModel();
        replyPost.userId = userId;
        replyPost.text = reply.getText();
        replyPost.media = reply.getMedia();
        replyPost.createdAt = java.time.LocalDateTime.now().toString();
        replyPost.replyPostId = postId.toString();
        replyPost.repostId = null;

        postRepository.persist(replyPost);

        return Response.ok(replyPost).build();
    }

    public Response createRepost(ObjectId postId, String userId) {
        PostModel post = postRepository.findById(postId);

        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PostModel repost = new PostModel();
        repost.userId = userId;
        repost.text = post.text;
        repost.media = post.media;
        repost.createdAt = java.time.LocalDateTime.now().toString();
        repost.replyPostId = post.replyPostId;
        repost.repostId = postId.toString();

        postRepository.persist(repost);

        return Response.ok(repost).build();
    }
}

