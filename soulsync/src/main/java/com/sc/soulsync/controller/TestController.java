package com.sc.soulsync.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok("Works fine!");
    }
}
