package com.diogo.nb.web2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class HealthService {

    private final DataSource dataSource;

    public String getStatus() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2) ? "ok" : "db unavailable";
        } catch (Exception e) {
            return "db unavailable";
        }
    }
}
