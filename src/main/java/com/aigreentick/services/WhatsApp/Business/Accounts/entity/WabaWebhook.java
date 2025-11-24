package com.aigreentick.services.WhatsApp.Business.Accounts.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "waba_webhooks")
@Data
public class WabaWebhook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long wabaAccountId;
    private String webhookUrl;
    private String verifyToken;

    private Instant createdAt = Instant.now();
}


