package com.orenda.country.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLogDto {
    private String username;
    private String method;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
    private String body;
}
