package com.orenda.country.service;


import com.orenda.country.common.pageResponse.PageResponse;
import com.orenda.country.dto.request.ProvinceRequest;
import com.orenda.country.dto.response.ProvinceResponse;



public interface ProvinceService {
    ProvinceResponse createProvince(ProvinceRequest request);
    ProvinceResponse updateProvince(ProvinceRequest request, int provinceId);
    ProvinceResponse getDetailProvince(int provinceId);
    void deleteProvince(int provinceId);
    PageResponse<ProvinceResponse> getAllProvince(String search, int pageNumber, int pageSize, String sortBy, String sortDir);
}
