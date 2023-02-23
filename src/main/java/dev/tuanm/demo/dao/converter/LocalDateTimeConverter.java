package dev.tuanm.demo.dao.converter;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String convertToDatabaseColumn(LocalDateTime value) {
        return Optional.ofNullable(value)
                .map(v -> v.toString(PATTERN))
                .orElse(null);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String text) {
        return Optional.ofNullable(text)
                .map(DateTimeFormat.forPattern(PATTERN)::parseLocalDateTime)
                .orElse(null);
    }
}
