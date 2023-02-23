package dev.tuanm.demo.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;

@Getter
@Setter
public class SearchResponse {
    private Long itemId;
    private String bicycleName;
    private String bicycleYear;
    private String brandName;
    private String modelName;
    private String description;
    private double price;
    private LocalDateTime listedAt;
}
