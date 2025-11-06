package com.orenda.country.common.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiResponse<T> {
    private int code = 200;
    private String message;
    private T result;

    public static <T> ApiResponse<T> success(String message, T result) {
        return  ApiResponse.<T>builder()
                .code(200)
                .message(message)
                .result(result)
                .build();
    }
    public static <T> ApiResponse<T> success(String message) {
        return  ApiResponse.<T>builder()
                .code(200)
                .message(message)
                .build();
    }
    public static <T> ApiResponse<T> success(T result) {
        return  ApiResponse.<T>builder()
                .code(200)
                .result(result)
                .build();
    }
}
