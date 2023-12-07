package com.muhammet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication

public class ElacticServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElacticServiceApplication.class);
    }
}
