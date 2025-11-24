package com.aigreentick.services.WhatsApp.Business.Accounts.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "waba_webhook_logs")
@Data
public class WabaWebhookLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;

    @Column(columnDefinition = "JSON")
    private String payload;

    private Instant createdAt = Instant.now();
}

