package com.aigreentick.services.WhatsApp.Business.Accounts.controller;

import com.aigreentick.services.WhatsApp.Business.Accounts.config.FacebookOAuthConfig;
import com.aigreentick.services.WhatsApp.Business.Accounts.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/waba/oauth")
@RequiredArgsConstructor
public class WabaOnboardingController {

    private final FacebookOAuthConfig fbConfig;

    @GetMapping("/login-url")
    public ApiResponse getLoginUrl() {

        String url = "https://www.facebook.com/" + fbConfig.getApiVersion() +
                "/dialog/oauth?client_id=" + fbConfig.getAppId() +
                "&redirect_uri=" + fbConfig.getRedirectUrl() +
                "&scope=business_management,whatsapp_business_messaging,whatsapp_business_management,pages_show_list";

        return ApiResponse.ok(url);
    }

    @GetMapping("/callback")
    public ApiResponse handleOAuthCallback(@RequestParam("code") String code) {
        return ApiResponse.ok("Received code: " + code);
    }
}

