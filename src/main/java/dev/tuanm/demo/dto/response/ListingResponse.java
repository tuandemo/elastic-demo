package dev.tuanm.demo.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;

@Getter
@Setter
public class ListingResponse {
    private Long id;
    private LocalDateTime listedAt;
}
