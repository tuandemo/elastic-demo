package dev.tuanm.demo.util;

import dev.tuanm.demo.dto.request.SearchRequest;
import org.springframework.lang.Nullable;

import java.util.Optional;

public final class SearchUtils {
    public static <T> T startOfRange(@Nullable SearchRequest.Range<T> range) {
        return Optional.ofNullable(range).map(SearchRequest.Range::getStart)
                .orElse(null);
    }

    public static <T> T endOfRange(@Nullable SearchRequest.Range<T> range) {
        return Optional.ofNullable(range).map(SearchRequest.Range::getEnd)
                .orElse(null);
    }
}
