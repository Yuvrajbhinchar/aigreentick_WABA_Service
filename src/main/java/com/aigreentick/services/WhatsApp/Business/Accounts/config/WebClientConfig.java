package com.aigreentick.services.WhatsApp.Business.Accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(FacebookOAuthConfig fbConfig) {

        return WebClient.builder()
                .baseUrl("https://graph.facebook.com/" + fbConfig.getApiVersion())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(config -> config.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
                                .build()
                )
                .build();
    }
}
