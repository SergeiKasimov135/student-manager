package ru.kasimov.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.kasimov.manager.client.RestClientStudentsRestClient;
import ru.kasimov.manager.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientStudentsRestClient studentsRestClient(
            @Value("${student-manager.services.registry.uri}") String registryBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${student-manager.services.registry.registration-id:keycloak}") String registrationId
    ) {
        return new RestClientStudentsRestClient(RestClient.builder()
                .baseUrl(registryBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        authorizedClientRepository), registrationId))
                .build());
    }

}
