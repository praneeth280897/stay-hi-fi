package com.example.stay_hi_fi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckerController {

    @GetMapping("/api/public/ping")
    public String ping() {
        log.info("Ping Successful");
        return "Server is awake!";
    }
}
