package com.orenda.country.controller;

import com.orenda.country.common.apiResponse.ApiResponse;
import com.orenda.country.common.pageResponse.PageResponse;
import com.orenda.country.dto.request.ProvinceRequest;
import com.orenda.country.dto.response.ProvinceResponse;
import com.orenda.country.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/province")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;
    @PostMapping
    ApiResponse<ProvinceResponse> createProvince(@RequestBody ProvinceRequest request){
        return ApiResponse.<ProvinceResponse>builder()
                .code(200)
                .result(provinceService.createProvince(request))
                .message("create province success")
                .build();
    }
    @PutMapping("/{provinceId}")
    ApiResponse<ProvinceResponse> updateProvince(
            @PathVariable int provinceId,
            @RequestBody ProvinceRequest request){
        return ApiResponse.<ProvinceResponse>builder()
                .code(200)
                .message("update province success")
                .result(provinceService.updateProvince(request, provinceId))
                .build();
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{provinceId}")
    ApiResponse<ProvinceResponse> getProvince(@PathVariable int provinceId){
        return ApiResponse.<ProvinceResponse>builder()
                .code(200)
                .message("get province success")
                .result(provinceService.getDetailProvince(provinceId))
                .build();

    }

    @DeleteMapping("/{provinceId}")
    ApiResponse<Void> deleteProvince(@PathVariable int provinceId){
        provinceService.deleteProvince(provinceId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("delete province success")
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<PageResponse<ProvinceResponse>> getAllProvince(
            @RequestParam(name = "search", required = false, defaultValue = "")String search,
            @RequestParam(name = "page_number", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "page_size", required = false, defaultValue ="5")Integer pageSize,
            @RequestParam(name = "sort_by", required = false, defaultValue="id")String sortBy,
            @RequestParam(name = "sort_dir", required = false, defaultValue="desc")String sortDir){
        return ApiResponse.<PageResponse<ProvinceResponse>>builder()
                .code(200)
                .message("get all provinces")
                .result(provinceService.getAllProvince(search, pageNumber, pageSize, sortBy, sortDir))
                .build();
    }
}
