package dev.tuanm.demo.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SearchRequest {
    private String query;
    private Range<Double> priceRange;
    private Range<String> dateRange;
    private Pageable page;

    @Getter
    @Setter
    public static class Range<T> {
        @NotNull
        private T start;
        @NotNull
        private T end;
    }
}
