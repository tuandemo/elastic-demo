package dev.tuanm.demo.dao.projection;

import org.joda.time.LocalDateTime;

public interface AggregationItem {
    Long getItemId();
    String getBicycleName();
    String getBicycleYear();
    String getBrandName();
    String getModelName();
    String getDescription();
    double getPrice();
    LocalDateTime getListedAt();
}
