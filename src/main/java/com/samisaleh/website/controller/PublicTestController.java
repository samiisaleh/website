package com.samisaleh.website.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicTestController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Application is running!";
    }
}