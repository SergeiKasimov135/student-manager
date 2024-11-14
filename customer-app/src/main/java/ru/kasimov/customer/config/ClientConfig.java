package ru.kasimov.customer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kasimov.customer.client.WebClientStudentsClient;

@Configuration
public class ClientConfig {

    @Bean
    public WebClientStudentsClient webClientStudentsClient(
            @Value("${student-manager.services.registry.uri:http://localhost:8081}") String baseUrl
    ) {
        return new WebClientStudentsClient(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

}
