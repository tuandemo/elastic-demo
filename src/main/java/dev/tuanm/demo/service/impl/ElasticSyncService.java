package dev.tuanm.demo.service.impl;

import dev.tuanm.demo.dao.elastic.Item;
import dev.tuanm.demo.repository.SearchRepository;
import dev.tuanm.demo.service.SyncService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ElasticSyncService implements SyncService {
    private static final int BULK_SIZE = 100;
    private final SearchRepository searchRepository;
    private final ElasticsearchOperations operations;

    public ElasticSyncService(
            SearchRepository searchRepository,
            ElasticsearchOperations operations) {
        this.searchRepository = searchRepository;
        this.operations = operations;
    }

    @Override
    public void sync() {
        this.start();
    }

    @SuppressWarnings("Duplicates")
    private void start() {
        List<IndexQuery> indexQueries = this.searchRepository.all().stream()
                .map(aggregationItem -> {
                    Item item = new Item();
                    item.setId(aggregationItem.getItemId());
                    item.setBicycleName(aggregationItem.getBicycleName());
                    item.setBicycleYear(aggregationItem.getBicycleYear());
                    item.setBrandName(aggregationItem.getBrandName());
                    item.setModelName(aggregationItem.getModelName());
                    item.setDescription(aggregationItem.getDescription());
                    item.setPrice(aggregationItem.getPrice());
                    item.setListedAt(aggregationItem.getListedAt().toDateTime().getMillis());
                    return new IndexQueryBuilder()
                            .withId(item.getId().toString())
                            .withObject(item)
                            .build();
                })
                .collect(Collectors.toList());
        if (indexQueries.size() < BULK_SIZE) {
            CompletableFuture.runAsync(() -> this.bulkIndex(indexQueries));
        } else {
            List<IndexQuery> bulkQueries = new ArrayList<>();
            indexQueries.forEach(indexQuery -> {
                bulkQueries.add(indexQuery);
                if (bulkQueries.size() % BULK_SIZE == 0) {
                    CompletableFuture.runAsync(() ->
                            this.bulkIndex(new ArrayList<>(bulkQueries)));
                    bulkQueries.clear();
                }
            });
        }
    }

    private void bulkIndex(List<IndexQuery> indexQueries) {
        try {
            this.operations.bulkIndex(indexQueries, IndexCoordinates.of("bbb"));
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }
}
