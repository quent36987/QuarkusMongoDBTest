package com.epita.domain.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.epita.data.model.PostModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;

@ApplicationScoped
public class PostService {

    @Inject
    ElasticsearchClient elasticsearchClient;

    public void createIndex() throws IOException {
        elasticsearchClient.indices().create(b -> b.index("posts"));
    }

    public void storePost(PostModel post) throws IOException {
        IndexRequest<PostModel> request = IndexRequest.of(b -> b.index("posts").id(post.post_id).document(post));

        elasticsearchClient.index(request);
    }

    public void deletePost(PostModel post) throws IOException {
        try {
            elasticsearchClient.delete(deleteRequest -> deleteRequest.index("posts").id(post.post_id));
        } catch (Exception e) {
            System.out.println("Post not found");
        }
    }
}
