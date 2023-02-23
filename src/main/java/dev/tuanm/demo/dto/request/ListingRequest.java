package dev.tuanm.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ListingRequest {
    @NotNull
    private Long bicycleId;
    private String description;
    private double price;
}
