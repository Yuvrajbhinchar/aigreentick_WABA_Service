package com.aigreentick.services.WhatsApp.Business.Accounts.service;

import com.aigreentick.services.WhatsApp.Business.Accounts.config.FacebookOAuthConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FacebookOAuthService {

    private final WebClient webClient;
    private final FacebookOAuthConfig fbConfig;
    private final ObjectMapper mapper = new ObjectMapper();

    public Mono<String> exchangeCodeForToken(String code) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/access_token")
                        .queryParam("client_id", fbConfig.getAppId())
                        .queryParam("client_secret", fbConfig.getAppSecret())
                        .queryParam("redirect_uri", fbConfig.getRedirectUrl())
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    public String extractAccessToken(String tokenJson) {
        try {
            return mapper.readTree(tokenJson).get("access_token").asText();
        } catch (Exception ex) {
            throw new RuntimeException("Invalid token response: " + tokenJson);
        }
    }
}

