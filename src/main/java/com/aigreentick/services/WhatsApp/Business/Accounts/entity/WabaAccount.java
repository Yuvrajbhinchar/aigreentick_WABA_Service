package com.aigreentick.services.WhatsApp.Business.Accounts.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "waba_accounts")
@Data
public class WabaAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long organizationId;
    private String wabaId;
    private String businessId;

    @Column(columnDefinition = "TEXT")
    private String accessToken;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    private Instant tokenExpiresAt;

    private Long createdBy;

    private Instant createdAt = Instant.now();
}

