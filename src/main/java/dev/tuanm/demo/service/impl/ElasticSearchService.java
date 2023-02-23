package dev.tuanm.demo.service.impl;

import dev.tuanm.demo.dao.elastic.Item;
import dev.tuanm.demo.dto.request.SearchRequest;
import dev.tuanm.demo.dto.response.SearchResponse;
import dev.tuanm.demo.service.SearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("ElasticSearchService")
public class ElasticSearchService implements SearchService {
    private final ElasticsearchOperations operations;

    public ElasticSearchService(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<SearchResponse> search(SearchRequest request) {
        Pageable page = request.getPage();
        String query = request.getQuery();
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(
                query,
                "bicycleName",
                "description");
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(page)
                .build();
        return this.operations.search(searchQuery, Item.class, IndexCoordinates.of("bbb"))
                .stream()
                .map(searchHit -> {
                    Item item = searchHit.getContent();
                    SearchResponse response = new SearchResponse();
                    response.setItemId(item.getId());
                    response.setBicycleName(item.getBicycleName());
                    response.setBicycleYear(item.getBicycleYear());
                    response.setBrandName(item.getBrandName());
                    response.setModelName(item.getModelName());
                    response.setDescription(item.getDescription());
                    response.setPrice(item.getPrice());
                    response.setListedAt(item.getListedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
