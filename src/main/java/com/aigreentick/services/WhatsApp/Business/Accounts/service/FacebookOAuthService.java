package com.aigreentick.services.WhatsApp.Business.Accounts.service;

import com.aigreentick.services.WhatsApp.Business.Accounts.config.FacebookOAuthConfig;
import com.fasterxml.jackson.databind.JsonNode;
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

    // 1️⃣ Exchange "code" → "access_token"
    public Mono<String> exchangeCodeForToken(String code) {

        String tokenUrl = "https://graph.facebook.com/" + fbConfig.getApiVersion()
                + "/oauth/access_token"
                + "?client_id=" + fbConfig.getAppId()
                + "&client_secret=" + fbConfig.getAppSecret()
                + "&redirect_uri=" + fbConfig.getRedirectUrl()
                + "&code=" + code;

        return webClient.get()
                .uri(tokenUrl)
                .retrieve()
                .bodyToMono(String.class);  // returns JSON as string
    }

    // 2️⃣ Extract access_token from JSON
    public String extractAccessToken(String tokenJson) {
        try {
            JsonNode node = mapper.readTree(tokenJson);
            return node.get("access_token").asText();
        } catch (Exception ex) {
            throw new RuntimeException("Could not parse access_token from response: " + tokenJson);
        }
    }
}
