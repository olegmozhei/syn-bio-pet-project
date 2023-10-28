package com.example.demo.controllers;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        JSONObject result = new JSONObject() {{
            put("hello", "world");
        }};
        return result.toString();
    }
}
