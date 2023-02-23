package dev.tuanm.demo.controller;

import dev.tuanm.demo.service.SyncService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {
    private final SyncService elasticSyncService;

    public SyncController(SyncService elasticSyncService) {
        this.elasticSyncService = elasticSyncService;
    }

    @PostMapping("sync")
    public boolean sync() {
        this.elasticSyncService.sync();
        return true;
    }
}
