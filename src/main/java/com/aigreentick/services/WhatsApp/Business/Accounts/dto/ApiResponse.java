package com.aigreentick.services.WhatsApp.Business.Accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    public static ApiResponse ok(Object data) {
        return new ApiResponse(true, "success", data);
    }

    public static ApiResponse error(String msg) {
        return new ApiResponse(false, msg, null);
    }
}

