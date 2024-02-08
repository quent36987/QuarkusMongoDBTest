package com.epita.domain.service;

import com.epita.domain.entity.PostEntity;
import com.epita.data.model.PostModel;
import com.epita.data.repository.PostRepository;

import com.epita.utils.converters.PostEntityToPostModel;
import com.epita.utils.converters.PostModelToPostEntity;
import com.epita.utils.errors.RestError;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PostService {

    @Inject
    PostRepository postRepository;
    @Inject
    PostModelToPostEntity postModelToPostEntity;

    public List<PostEntity> getAllPosts() {
        List<PostModel> posts = postRepository.findAll().stream().toList();

        return postModelToPostEntity.convertList(posts);
    }

    public PostEntity getPostById(String postId) {
        PostModel post = postRepository.findById(new ObjectId(postId));

        if (post == null) {
            throw RestError.POST_NOT_FOUND.get(postId);
        }

        return postModelToPostEntity.convert(post);
    }

    public List<PostEntity> getRepliesByPostId(String postId) {
        List<PostModel> posts = postRepository.find("replyPostId", postId).list();

        return postModelToPostEntity.convertList(posts);
    }

    public boolean existById(String postId) {
        return postRepository.findById(new ObjectId(postId)) != null;
    }


    public PostEntity createPost(String userId, String text, String media) {
        PostModel postModel = new PostModel().withUserId(userId).withCreatedAt(LocalDateTime.now()).withText(text).withMedia(media);

        postRepository.persist(postModel);

        return postModelToPostEntity.convert(postModel);
    }


    public PostEntity createReply(String userId, String text, String media, String postId) {
        if (postRepository.findById(new ObjectId(postId)) == null) {
            throw RestError.POST_NOT_FOUND.get(postId);
        }

        PostModel postModel = new PostModel().withUserId(userId).withCreatedAt(LocalDateTime.now()).withText(text).withMedia(media).withReplyPostId(postId);

        postRepository.persist(postModel);

        return postModelToPostEntity.convert(postModel);
    }


    public PostEntity createRepost(String userId, String postId) {
        PostModel post = postRepository.findById(new ObjectId(postId));

        if (post == null) {
            throw RestError.POST_NOT_FOUND.get(postId);
        }

        PostModel postModel = new PostModel().withUserId(userId).withCreatedAt(LocalDateTime.now()).withText(post.getText()).withMedia(post.getMedia()).withRepostId(postId);

        postRepository.persist(postModel);

        return postModelToPostEntity.convert(postModel);
    }

    public boolean deletePost(String userId, String postId) {
        PostModel post = postRepository.findById(new ObjectId(postId));

        if (post == null || !post.userId.equals(userId)) {
            return false;
        }

        return postRepository.deleteById(new ObjectId(postId));
    }
}
