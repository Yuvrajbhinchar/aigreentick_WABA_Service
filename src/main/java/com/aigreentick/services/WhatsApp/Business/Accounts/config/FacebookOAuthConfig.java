package com.aigreentick.services.WhatsApp.Business.Accounts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@Data
public class FacebookOAuthConfig {

    @Value("${facebook.app.id}")
    private String appId;

    @Value("${facebook.app.secret}")
    private String appSecret;

    @Value("${facebook.redirect.url}")
    private String redirectUrl;

    @Value("${facebook.api.version:v22.0}")
    private String apiVersion;
}

