package com.example.stay_hi_fi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckerController {

    @GetMapping("/api/public/ping")
    public String ping() {
        return "Server is awake!";
    }
}
