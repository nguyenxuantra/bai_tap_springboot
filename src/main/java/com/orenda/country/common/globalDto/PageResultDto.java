package com.orenda.country.common.globalDto;


import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class PageResultDto<T> {
    private long totalCount;
    private List<T> items;

    public PageResultDto(long totalCount, List<T> items) {
        this.totalCount = totalCount;
        this.items = items;
    }
    public static <T> PageResultDto<T> empty(){
        return new PageResultDto<>(0, Collections.emptyList());
    }
}
