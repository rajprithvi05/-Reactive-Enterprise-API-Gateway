package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public String myFallbackMessage() {
        return "{\"error\": \"EMERGENCY OVERRIDE: The backend server is down. The Circuit Breaker has safely caught this request!\"}";
    }
}