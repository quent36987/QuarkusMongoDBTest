package com.epita.domain.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.epita.data.model.PostModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SearchPostService {

    @Inject
    ElasticsearchClient elasticsearchClient;

    public List<PostModel> searchPosts(String keywords, String hashtag) throws IOException {
        List<String> hashtags = Arrays.stream(hashtag.split(",")).toList();
        List<Query> queries = new ArrayList<>();

        for (String hash : hashtags) {
            Query queryBuilders = QueryBuilders.match(b3 -> b3.field("text").query(hash));

            queries.add(queryBuilders);
        }

        SearchRequest searchRequest = SearchRequest.of(b -> b.index("posts").query(QueryBuilders.bool(b1 -> b1.should(QueryBuilders.match(b2 -> b2.field("text").query(keywords))).must(queries))));

        SearchResponse<PostModel> searchResponse = elasticsearchClient.search(searchRequest, PostModel.class);
        HitsMetadata<PostModel> hits = searchResponse.hits();
        return hits.hits().stream().map(hit -> hit.source()).collect(Collectors.toList());
    }
}
