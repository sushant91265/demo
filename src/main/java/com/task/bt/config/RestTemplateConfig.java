package com.task.bt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Value("${external.api.timeout}")
    private final int externalApiTimeout = 5000;
    @Value("${external.api.connection.timeout}")
    private final int connectionTimeout = 5000;
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectionTimeout);
        factory.setReadTimeout(externalApiTimeout);

        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }
}