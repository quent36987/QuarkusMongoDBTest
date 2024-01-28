package com.epita.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.epita.model.PostModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SearchPostService {

    @Inject
    ElasticsearchClient elasticsearchClient;

    public List<PostModel> searchPosts(String keywords, List<String> hashtags) throws IOException {
        // If regular words are in the search terms, results must contain AT LEAST ONE of the
        //searched words (vague search).
        //□ If hashtags are in the search terms, results must include ALL of the searched hashtags (strict
        //search)
        //□ If both words and hashtags are in the search terms, results must fulfill BOTH rules above
        //at once
        //□ A hashtag word should not be matched as a regular word, only as a hashtag (e.g. searching
        //“word” should not find “#word”)

        SearchRequest searchRequest = SearchRequest.of(b -> b
                .index("posts")
                .query(QueryBuilders.bool(b1 -> b1
                        .must(QueryBuilders.match(b2 -> b2
                                .field("content")
                                .query(keywords)))
                        .must(QueryBuilders.match(b3 -> b3
                                .field("hashtags")
                                .query(hashtags.stream().collect(Collectors.joining(" "))))))));

        SearchResponse<PostModel> searchResponse = elasticsearchClient.search(searchRequest, PostModel.class);
        HitsMetadata<PostModel> hits = searchResponse.hits();
        return hits.hits().stream().map(hit -> hit.source()).collect(Collectors.toList());
    }
}
