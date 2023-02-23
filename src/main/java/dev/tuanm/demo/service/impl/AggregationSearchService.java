package dev.tuanm.demo.service.impl;

import dev.tuanm.demo.dto.request.SearchRequest;
import dev.tuanm.demo.dto.response.SearchResponse;
import dev.tuanm.demo.repository.SearchRepository;
import dev.tuanm.demo.service.SearchService;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("AggregationSearchService")
public class AggregationSearchService implements SearchService {
    private final SearchRepository repository;

    public AggregationSearchService(SearchRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<SearchResponse> search(SearchRequest request) {
        Pageable page = request.getPage();
        String query = request.getQuery();
        Double startPrice = startOfRange(request.getPriceRange());
        Double endPrice = endOfRange(request.getPriceRange());
        String startDate = startOfRange(request.getDateRange());
        String endDate = endOfRange(request.getDateRange());
        return this.repository.search(page, query, startPrice, endPrice, startDate, endDate)
                .stream()
                .map(item -> {
                    SearchResponse response = new SearchResponse();
                    response.setItemId(item.getItemId());
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

    private static <T> T startOfRange(@Nullable SearchRequest.Range<T> range) {
        return Optional.ofNullable(range).map(SearchRequest.Range::getStart)
                .orElse(null);
    }

    private static <T> T endOfRange(@Nullable SearchRequest.Range<T> range) {
        return Optional.ofNullable(range).map(SearchRequest.Range::getEnd)
                .orElse(null);
    }
}
