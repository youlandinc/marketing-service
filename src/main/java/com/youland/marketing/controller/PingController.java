package com.youland.marketing.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class PingController {

    private static final String template = "%s v%s. Ping %d! %s";
    private final AtomicLong counter = new AtomicLong();

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.name}")
    private String appName;

    @GetMapping
    public String ping() {
        return String.format(
                template, this.appName, this.appVersion, counter.incrementAndGet(), Instant.now().toString());
    }
}
