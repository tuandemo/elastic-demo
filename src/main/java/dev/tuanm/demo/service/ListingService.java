package dev.tuanm.demo.service;

import dev.tuanm.demo.dto.request.ListingRequest;
import dev.tuanm.demo.dto.response.ListingResponse;

public interface ListingService {
    ListingResponse create(ListingRequest request);
}
