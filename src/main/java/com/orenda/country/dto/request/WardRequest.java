package com.orenda.country.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WardRequest {
    private String name;
    private String code;
    private String provinceCode;
}
