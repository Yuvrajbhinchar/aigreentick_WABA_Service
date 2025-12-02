package com.aigreentick.services.WhatsApp.Business.Accounts.service;

import com.aigreentick.services.WhatsApp.Business.Accounts.entity.WabaAccount;
import com.aigreentick.services.WhatsApp.Business.Accounts.repositories.WabaAccountRepository;
import com.aigreentick.services.WhatsApp.Business.Accounts.repositories.WabaPhoneNumberRepository;
import com.aigreentick.services.WhatsApp.Business.Accounts.entity.WabaPhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WabaOnboardingService {

    private final WebClient webClient;
    private final WabaAccountRepository wabaAccountRepo;
    private final WabaPhoneNumberRepository phoneRepo;

    public Mono<Map<String, Object>> completeOnboarding(
            String accessToken,
            Long organizationId,
            Long userId) {

        return fetchBusinessId(accessToken)
                .flatMap(businessId ->
                        fetchWabaId(businessId, accessToken)
                                .flatMap(wabaId ->
                                        saveWabaAccount(wabaId, businessId, accessToken, organizationId, userId)
                                                .flatMap(saved ->
                                                        fetchPhoneNumbers(wabaId, accessToken)
                                                                .flatMap(numbers ->
                                                                        savePhoneNumbers(saved.getId(), numbers)
                                                                                .thenReturn(Map.of(
                                                                                        "business_id", businessId,
                                                                                        "waba_id", wabaId,
                                                                                        "phone_numbers", numbers
                                                                                ))
                                                                )
                                                )
                                )
                );
    }

    private Mono<String> fetchBusinessId(String token) {
        return webClient.get()
                .uri("/me/businesses?access_token=" + token)
                .retrieve()
                .bodyToMono(Map.class)
                .map(json -> {
                    var list = (List<Map<String, Object>>) json.get("data");
                    if (list.isEmpty()) throw new RuntimeException("No business accounts found");
                    return list.get(0).get("id").toString();
                });
    }

    private Mono<String> fetchWabaId(String businessId, String token) {
        return webClient.get()
                .uri("/" + businessId + "/owned_whatsapp_business_accounts?access_token=" + token)
                .retrieve()
                .bodyToMono(Map.class)
                .map(json -> {
                    var list = (List<Map<String, Object>>) json.get("data");
                    if (list.isEmpty()) throw new RuntimeException("No WABA found");
                    return list.get(0).get("id").toString();
                });
    }

    private Mono<WabaAccount> saveWabaAccount(String wabaId,
                                              String businessId,
                                              String token,
                                              Long orgId,
                                              Long userId) {

        return Mono.fromCallable(() -> {
            WabaAccount acc = new WabaAccount();
            acc.setWabaId(wabaId);
            acc.setBusinessId(businessId);
            acc.setOrganizationId(orgId);
            acc.setAccessToken(token);
            acc.setCreatedBy(userId);
            acc.setCreatedAt(Instant.now());
            return wabaAccountRepo.save(acc);
        });
    }

    private Mono<List<Map<String, Object>>> fetchPhoneNumbers(String wabaId, String token) {
        return webClient.get()
                .uri("/" + wabaId + "/phone_numbers?access_token=" + token)
                .retrieve()
                .bodyToMono(Map.class)
                .map(json -> (List<Map<String, Object>>) json.get("data"));
    }

    private Mono<Void> savePhoneNumbers(Long wabaAccountId,
                                        List<Map<String, Object>> numbers) {

        return Mono.fromRunnable(() -> numbers.forEach(p -> {
            WabaPhoneNumber ph = new WabaPhoneNumber();
            ph.setWabaAccountId(wabaAccountId);
            ph.setPhoneNumberId(p.get("id").toString());
            ph.setDisplayPhoneNumber(p.get("display_phone_number").toString());
            ph.setCreatedAt(Instant.now());
            phoneRepo.save(ph);
        }));
    }
}
