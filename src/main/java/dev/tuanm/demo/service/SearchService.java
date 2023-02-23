package dev.tuanm.demo.service;

import dev.tuanm.demo.dto.request.SearchRequest;
import dev.tuanm.demo.dto.response.SearchResponse;

import java.util.List;

public interface SearchService {
    List<SearchResponse> search(SearchRequest request);
}
