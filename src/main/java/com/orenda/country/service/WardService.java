package com.orenda.country.service;

import com.orenda.country.common.pageResponse.PageResponse;
import com.orenda.country.dto.request.WardRequest;
import com.orenda.country.dto.response.WardResponse;
import org.springframework.data.domain.PageRequest;

public interface WardService {
    WardResponse createWard(WardRequest request);
    WardResponse updateWard(WardRequest request, int wardId);
    void deleteWard(int wardId);
    WardResponse getWard(int wardId);
    PageResponse<WardResponse> getWards(String search, String provinceCode, int pageNumber, int pageSize, String sortBy, String sortDir);
}
