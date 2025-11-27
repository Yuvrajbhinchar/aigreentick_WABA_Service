package com.aigreentick.services.WhatsApp.Business.Accounts.controller;

import com.aigreentick.services.WhatsApp.Business.Accounts.config.FacebookOAuthConfig;
import com.aigreentick.services.WhatsApp.Business.Accounts.dto.ApiResponse;
import com.aigreentick.services.WhatsApp.Business.Accounts.service.FacebookOAuthService;
import com.aigreentick.services.WhatsApp.Business.Accounts.service.WabaOnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/waba/oauth")
@RequiredArgsConstructor
public class WabaOnboardingController {

    private final FacebookOAuthConfig fbConfig;
    private final FacebookOAuthService oAuthService;
    private final WabaOnboardingService onboardingService;

    // 1️⃣ Generate Facebook OAuth URL
    @GetMapping("/login-url")
    public ApiResponse getLoginUrl() {

        String url = "https://www.facebook.com/" + fbConfig.getApiVersion()
                + "/dialog/oauth?client_id=" + fbConfig.getAppId()
                + "&redirect_uri=" + fbConfig.getRedirectUrl()
                + "&scope=business_management,"
                + "whatsapp_business_management,"
                + "whatsapp_business_messaging,"
                + "pages_show_list";

        return ApiResponse.ok(url);
    }

    // 2️⃣ Meta callback with ?code=
    @GetMapping("/callback")
    public Mono<ApiResponse> callback(@RequestParam String code,
                                      @RequestParam Long organizationId,
                                      @RequestParam Long userId) {

        return oAuthService.exchangeCodeForToken(code)
                .flatMap(tokenJson -> {
                    String token = oAuthService.extractAccessToken(tokenJson);
                    return onboardingService.completeOnboarding(token, organizationId, userId);
                })
                .map(ApiResponse::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(e.getMessage())));
    }
}
