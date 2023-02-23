package dev.tuanm.demo.controller;

import dev.tuanm.demo.dto.request.SearchRequest;
import dev.tuanm.demo.dto.response.SearchResponse;
import dev.tuanm.demo.service.SearchService;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
public class SearchController {
    private final ApplicationContext context;

    public SearchController(ApplicationContext context) {
        this.context = context;
    }

    @PostMapping("search")
    public List<SearchResponse> search(
            @RequestParam(required = false) String elastic,
            @NotNull @RequestBody SearchRequest request,
            Pageable page) {
        request.setPage(page);
        final String serviceName = Objects.equals(elastic, "true")
                ? "ElasticSearchService"
                : "AggregationSearchService";
        return this.context.getBean(serviceName, SearchService.class)
                .search(request);
    }
}
