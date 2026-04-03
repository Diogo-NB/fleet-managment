package com.diogo.nb.web2.controller;

import com.diogo.nb.web2.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final HealthService healthService;

    @GetMapping("/health")
    public String health() {
        return healthService.getStatus();
    }
}
