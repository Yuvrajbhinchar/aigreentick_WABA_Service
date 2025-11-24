package com.aigreentick.services.WhatsApp.Business.Accounts.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "waba_phone_numbers")
@Data
public class WabaPhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long wabaAccountId;
    private String phoneNumberId;
    private String displayPhoneNumber;
    private String verifiedName;
    private String status;
    private String qualityRating;

    private Instant createdAt = Instant.now();
}

