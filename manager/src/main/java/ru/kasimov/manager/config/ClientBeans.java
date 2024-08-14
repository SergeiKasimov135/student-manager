package ru.kasimov.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.kasimov.manager.client.RestClientStudentsRestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientStudentsRestClient studentsRestClient(
            @Value("${student-manager.services.registry.uri}") String registryBaseUri
    ) {
        return new RestClientStudentsRestClient(
                RestClient.builder().baseUrl(registryBaseUri).build()
        );
    }

}
