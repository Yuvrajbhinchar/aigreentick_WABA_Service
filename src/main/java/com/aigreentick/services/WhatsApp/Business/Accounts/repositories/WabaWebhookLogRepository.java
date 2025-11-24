package com.aigreentick.services.WhatsApp.Business.Accounts.repositories;

import com.aigreentick.services.WhatsApp.Business.Accounts.entity.WabaWebhookLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WabaWebhookLogRepository extends JpaRepository<WabaWebhookLog, Long> {}

