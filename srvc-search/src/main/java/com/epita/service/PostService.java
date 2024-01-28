package com.epita.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.epita.model.PostModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostService {

    @Inject
    ElasticsearchClient elasticsearchClient;

    public void storePost(PostModel post) throws IOException {
        IndexRequest<PostModel> request = IndexRequest.of(
                b -> b.index("posts")
                        .id(post.post_id)
                        .document(post));

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
