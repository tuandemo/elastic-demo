package dev.tuanm.demo.service.impl;

import dev.tuanm.demo.dao.elastic.Item;
import dev.tuanm.demo.dto.request.SearchRequest;
import dev.tuanm.demo.dto.response.SearchResponse;
import dev.tuanm.demo.service.SearchService;
import dev.tuanm.demo.util.SearchUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
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
        Double startPrice = SearchUtils.startOfRange(request.getPriceRange());
        Double endPrice = SearchUtils.endOfRange(request.getPriceRange());
        String startDate = SearchUtils.startOfRange(request.getDateRange());
        String endDate = SearchUtils.endOfRange(request.getDateRange());
        QueryBuilder bicycleNameQueryBuilder = QueryBuilders
                .matchQuery("bicycleName", query)
                .fuzziness(1);
        QueryBuilder modelNameQueryBuilder = QueryBuilders
                .matchQuery("modelName", query)
                .fuzziness(1);
        QueryBuilder brandNameQueryBuilder = QueryBuilders
                .matchQuery("brandName", query)
                .fuzziness(1);
        QueryBuilder descriptionQueryBuilder = QueryBuilders
                .matchQuery("description", query)
                .fuzziness(1);
        QueryBuilder priceQueryBuilder = QueryBuilders
                .rangeQuery("price")
                .gte(startPrice)
                .lte(endPrice);
        QueryBuilder dateQueryBuilder = QueryBuilders
                .rangeQuery("listedAt")
                .gte(startDate)
                .lte(endDate);
        QueryBuilder boolQueryBuilder = QueryBuilders
                .boolQuery()
                .should(bicycleNameQueryBuilder)
                .should(modelNameQueryBuilder)
                .should(brandNameQueryBuilder)
                .should(descriptionQueryBuilder)
                .minimumShouldMatch(1)
                .filter(priceQueryBuilder)
                .filter(dateQueryBuilder);
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
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
                    response.setListedAt(LocalDateTime.fromDateFields(
                            Date.from(Instant.ofEpochMilli(item.getListedAt()))));
                    return response;
                })
                .collect(Collectors.toList());
    }
}
