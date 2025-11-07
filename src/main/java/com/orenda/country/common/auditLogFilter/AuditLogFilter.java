package com.orenda.country.common.auditLogFilter;

import com.orenda.country.dto.AuditLogDto;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;


@Component
public class AuditLogFilter implements Filter {
    private final KafkaTemplate<String, AuditLogDto> kafkaTemplate;

    public AuditLogFilter(KafkaTemplate<String, AuditLogDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        AuditLogDto auditLogDto = AuditLogDto.builder()
                .username(httpReq.getRemoteUser() != null ? httpReq.getRemoteUser() : "anonymous")
                .method(httpReq.getMethod())
                .uri(httpReq.getRequestURI())
                .ip(httpReq.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        // Gửi message leen Kafka
        kafkaTemplate.send("audit-log", auditLogDto);
        chain.doFilter(request, response);
    }
}
