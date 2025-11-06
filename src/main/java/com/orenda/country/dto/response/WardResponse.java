package com.orenda.country.dto.response;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class WardResponse {
    private int id;
    private String name;
    private String code;
    private String provinceCode;
    private String provinceName;
}
