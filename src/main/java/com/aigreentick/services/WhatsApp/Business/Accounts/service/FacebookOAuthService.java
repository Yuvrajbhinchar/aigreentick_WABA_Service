package com.aigreentick.services.WhatsApp.Business.Accounts.service;

import com.aigreentick.services.WhatsApp.Business.Accounts.config.FacebookOAuthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FacebookOAuthService {

    private final WebClient webClient;
    private final FacebookOAuthConfig fbConfig;

    public Mono<String> exchangeCodeForToken(String code) {

        String tokenUrl = "https://graph.facebook.com/" + fbConfig.getApiVersion() +
                "/oauth/access_token" +
                "?client_id=" + fbConfig.getAppId() +
                "&client_secret=" + fbConfig.getAppSecret() +
                "&redirect_uri=" + fbConfig.getRedirectUrl() +
                "&code=" + code;

        return webClient.get()
                .uri(tokenUrl)
                .retrieve()
                .bodyToMono(String.class);
    }
}
