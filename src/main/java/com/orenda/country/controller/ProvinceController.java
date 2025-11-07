package com.orenda.country.controller;

import com.orenda.country.common.apiResponse.ApiResponse;
import com.orenda.country.common.pageResponse.PageResponse;
import com.orenda.country.dto.request.ProvinceRequest;
import com.orenda.country.dto.response.ProvinceResponse;
import com.orenda.country.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/province")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;
    private final MessageSource messageSource;

    @PreAuthorize("hasAuthority('CREATE_PROVINCE')")
    @PostMapping
    ApiResponse<ProvinceResponse> createProvince(@RequestBody ProvinceRequest request){
        String message = messageSource.getMessage("create.success", null, LocaleContextHolder.getLocale());
        return ApiResponse.<ProvinceResponse>builder()
                .code(200)
                .result(provinceService.createProvince(request))
                .message(message)
                .build();
    }
    @PreAuthorize("hasAuthority('UPDATE_PROVINCE')")
    @PutMapping("/{provinceId}")
    ApiResponse<ProvinceResponse> updateProvince(
            @PathVariable int provinceId,
            @RequestBody ProvinceRequest request){
        String message = messageSource.getMessage("put.success", null, LocaleContextHolder.getLocale());
        return ApiResponse.<ProvinceResponse>builder()
                .code(200)
                .message(message)
                .result(provinceService.updateProvince(request, provinceId))
                .build();
    }
    @PreAuthorize("hasAuthority('GET_PROVINCE')")
    @GetMapping("/{provinceId}")
    ApiResponse<ProvinceResponse> getProvince(@PathVariable int provinceId){
        String message = messageSource.getMessage("get.success", null, LocaleContextHolder.getLocale());
        return ApiResponse.<ProvinceResponse>builder()
                .code(200)
                .message(message)
                .result(provinceService.getDetailProvince(provinceId))
                .build();

    }

    @PreAuthorize("hasAuthority('DELETE_PROVINCE')")
    @DeleteMapping("/{provinceId}")
    ApiResponse<Void> deleteProvince(@PathVariable int provinceId){
        String message = messageSource.getMessage("delete.success", null, LocaleContextHolder.getLocale());
        provinceService.deleteProvince(provinceId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message(message)
                .build();
    }
    @PreAuthorize("hasAuthority('GET_ALL_PROVINCE')")
    @GetMapping
    public ApiResponse<PageResponse<ProvinceResponse>> getAllProvince(
            @RequestParam(name = "search", required = false, defaultValue = "")String search,
            @RequestParam(name = "page_number", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "page_size", required = false, defaultValue ="5")Integer pageSize,
            @RequestParam(name = "sort_by", required = false, defaultValue="id")String sortBy,
            @RequestParam(name = "sort_dir", required = false, defaultValue="desc")String sortDir){
        String message = messageSource.getMessage("get.success", null, LocaleContextHolder.getLocale());

        var a = provinceService.getAllProvince(search, pageNumber, pageSize, sortBy, sortDir);
        return ApiResponse.<PageResponse<ProvinceResponse>>builder()
                .code(200)
                .message(message)
                .result(a)
                .build();
    }
}
