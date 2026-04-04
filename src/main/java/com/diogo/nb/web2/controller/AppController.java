package com.diogo.nb.web2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final DataSource dataSource;

    @GetMapping("/health")
    public String health() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2) ? "ok" : "db unavailable";
        } catch (Exception e) {
            return "db unavailable";
        }
    }
}
