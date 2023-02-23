package dev.tuanm.demo.util;

import org.joda.time.LocalDateTime;
import org.springframework.lang.Nullable;

import java.util.Optional;

public final class DateTimeUtils {
    public static LocalDateTime toLocalDateTime(@Nullable String text) {
        return Optional.ofNullable(text)
                .map(LocalDateTime::parse)
                .orElse(null);
    }
}
