package dev.tuanm.demo.service.impl;

import dev.tuanm.demo.dto.request.SearchRequest;
import dev.tuanm.demo.dto.response.SearchResponse;
import dev.tuanm.demo.repository.SearchRepository;
import dev.tuanm.demo.service.SearchService;
import dev.tuanm.demo.util.DateTimeUtils;
import dev.tuanm.demo.util.SearchUtils;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Double startPrice = SearchUtils.startOfRange(request.getPriceRange());
        Double endPrice = SearchUtils.endOfRange(request.getPriceRange());
        LocalDateTime startDate = DateTimeUtils.toLocalDateTime(
                SearchUtils.startOfRange(request.getDateRange()));
        LocalDateTime endDate = DateTimeUtils.toLocalDateTime(
                SearchUtils.endOfRange(request.getDateRange()));
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
}
