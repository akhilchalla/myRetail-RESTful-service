package com.example.myretail.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ApplicationConfiguration {

    @Bean("restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder
                .setReadTimeout(Duration.ofSeconds(20))
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();
    }
}
