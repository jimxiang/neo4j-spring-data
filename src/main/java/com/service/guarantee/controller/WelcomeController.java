package com.service.guarantee.controller;

import com.service.guarantee.configuration.Neo4jConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    private final Neo4jConfiguration configuration;

    @Autowired
    public WelcomeController(Neo4jConfiguration configuration) {
        this.configuration = configuration;
    }

    @RequestMapping("/dynamic-configuration")
    public Map<String, Object> dynamicConfiguration() {
        Map<String, Object> map = new HashMap<>();
        map.put("uri", configuration.getUri());
        map.put("username", configuration.getUsername());
        map.put("password", configuration.getPassword());
        return map;
    }
}
