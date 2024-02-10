package com.hapjuhasil.server.dto;

import lombok.NonNull;

public record CommonResponse(
        boolean success,
        @NonNull String message,
        Object data
) {

    public static CommonResponse success(String message, Object data) {
        return new CommonResponse(true, message, data);
    }

    public static CommonResponse success(String message) {
        return new CommonResponse(true, message, null);
    }

    public static CommonResponse fail(String message) {
        return new CommonResponse(false, message, null);
    }
}
