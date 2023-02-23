package dev.tuanm.demo.service.impl;

import dev.tuanm.demo.dao.entity.Item;
import dev.tuanm.demo.dao.projection.AggregationItem;
import dev.tuanm.demo.dto.request.ListingRequest;
import dev.tuanm.demo.dto.response.ListingResponse;
import dev.tuanm.demo.repository.SearchRepository;
import dev.tuanm.demo.repository.ListingRepository;
import dev.tuanm.demo.service.ListingService;
import org.joda.time.LocalDateTime;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ListingServiceImpl implements ListingService {
    private final ListingRepository listingRepository;
    private final SearchRepository searchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public ListingServiceImpl(
            ListingRepository listingRepository,
            SearchRepository searchRepository,
            ElasticsearchOperations elasticsearchOperations) {
        this.listingRepository = listingRepository;
        this.searchRepository = searchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Transactional
    @Override
    public ListingResponse create(ListingRequest request) {
        Item item = new Item();
        item.setBicycleId(request.getBicycleId());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setListedAt(LocalDateTime.now());
        return this.searchRepository.findByItemId(this.listingRepository.save(item).getId())
                .map(saved -> {
                    this.index(saved);
                    ListingResponse response = new ListingResponse();
                    response.setId(saved.getItemId());
                    response.setListedAt(saved.getListedAt());
                    return response;
                })
                .orElse(null);
    }

    @SuppressWarnings("Duplicates")
    private void index(AggregationItem aggregationItem) {
        Optional.ofNullable(aggregationItem)
                .map(i -> new dev.tuanm.demo.dao.elastic.Item())
                .map(item -> {
                    item.setId(aggregationItem.getItemId());
                    item.setBicycleName(aggregationItem.getBicycleName());
                    item.setBicycleYear(aggregationItem.getBicycleYear());
                    item.setBrandName(aggregationItem.getBrandName());
                    item.setModelName(aggregationItem.getModelName());
                    item.setDescription(aggregationItem.getDescription());
                    item.setPrice(aggregationItem.getPrice());
                    item.setListedAt(aggregationItem.getListedAt());
                    return item;
                })
                .ifPresent(item -> {
                    IndexQuery indexQuery = new IndexQueryBuilder()
                            .withId(item.getId().toString())
                            .withObject(item)
                            .build();
                    this.elasticsearchOperations.index(indexQuery, IndexCoordinates.of("bbb"));
                });
    }
}
