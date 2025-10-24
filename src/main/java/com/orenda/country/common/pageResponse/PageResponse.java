package com.orenda.country.common.pageResponse;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PageResponse <T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean isLast;
}
