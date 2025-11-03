package com.orenda.country.common.globalDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.io.Serial;

public class PagedResultRequestDto {

    private Integer maxResultCount = 10;
    private Integer skipCount = 0;
    private String sorting;
    private String filter;

    @JsonIgnore
    public PageRequest getPageRequest() {
        int pageIndex = 0;
        if(maxResultCount != null || maxResultCount <=0){
            maxResultCount = 10;
        }
        if(skipCount == null || skipCount<0){
            skipCount = 0;
        }
        Integer pageSize = maxResultCount;
        pageIndex = ((skipCount)/maxResultCount);
        return PageRequest.of(pageIndex, pageSize,getSort() );
    }

    @JsonIgnore
    public Sort getSort() {
        if(sorting == null || sorting.isEmpty()){
            return Sort.unsorted();
        }
        String[] sortFields = sorting.split(",");
        Sort sort = Sort.unsorted();
        for(String field: sortFields){
            String[] parts = field.trim().split("\\s+");
            if(parts.length ==2){
                Sort.Direction direction = parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                sort = sort.and(Sort.by(direction, parts[0]));
            } else {
                sort = sort.and(Sort.by(parts[0]));
            }
        }
        return sort;
    }

}
