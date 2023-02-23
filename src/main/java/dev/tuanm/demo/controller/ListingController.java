package dev.tuanm.demo.controller;

import dev.tuanm.demo.dto.request.ListingRequest;
import dev.tuanm.demo.dto.response.ListingResponse;
import dev.tuanm.demo.service.ListingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class ListingController {
    private final ListingService service;

    public ListingController(ListingService service) {
        this.service = service;
    }

    @PostMapping("list")
    public ListingResponse list(
            @NotNull @RequestBody ListingRequest request) {
        return this.service.create(request);
    }
}
