package com.orenda.country.controller;


import com.orenda.country.common.apiResponse.ApiResponse;
import com.orenda.country.common.pageResponse.PageResponse;
import com.orenda.country.dto.request.WardRequest;
import com.orenda.country.dto.response.WardResponse;
import com.orenda.country.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/ward")
@RequiredArgsConstructor
public class WardController {
    private final WardService wardService;
    private final MessageSource messageSource;

    @PreAuthorize("hasAuthority('CREATE_WARD')")
    @PostMapping
    ApiResponse<WardResponse> createWard(@RequestBody WardRequest wardRequest){
        String message = messageSource.getMessage("create.success", null, LocaleContextHolder.getLocale());
        return ApiResponse.<WardResponse>builder()
                .code(200)
                .message(message)
                .result(wardService.createWard(wardRequest))
                .build();
    }
    @PreAuthorize("hasAuthority('UPDATE_WARD')")
    @PutMapping("/{wardId}")
    ApiResponse<WardResponse> updateWard(@RequestBody WardRequest wardRequest, @PathVariable int wardId){
        String message = messageSource.getMessage("put.success", null, LocaleContextHolder.getLocale());
        return ApiResponse.<WardResponse>builder()
                .code(200)
                .message(message)
                .result(wardService.updateWard(wardRequest, wardId))
                .build();
    }
    @PreAuthorize("hasAuthority('DELETE_WARD')")
    @DeleteMapping("/{wardId}")
    ApiResponse<Void> deleteWard(@PathVariable int wardId){
        String message = messageSource.getMessage("delete.success", null, LocaleContextHolder.getLocale());
        wardService.deleteWard(wardId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message(message)
                .build();
    }
    @PreAuthorize("hasAuthority('GET_WARD')")
    @GetMapping("/{wardId}")
    ApiResponse<WardResponse> getWard(@PathVariable int wardId){
        String message = messageSource.getMessage("get.success", null, LocaleContextHolder.getLocale());
        return ApiResponse.<WardResponse>builder()
                .code(200)
                .message(message)
                .result(wardService.getWard(wardId))
                .build();
    }
    @PreAuthorize("hasAuthority('GET_WARDS')")
    @GetMapping
    ApiResponse<PageResponse<WardResponse>> getWards(
            @RequestParam(name = "search", required = false, defaultValue="")String search,
            @RequestParam(name = "province_code", required = false)String provinceCode,
            @RequestParam(name = "page_number", required = false, defaultValue="1")int pageNumber,
            @RequestParam(name = "page_size", required = false, defaultValue = "5")int pageSize,
            @RequestParam(name = "sort_by", required =false, defaultValue = "id")String sortBy,
            @RequestParam(name = "sort_dir", required = false, defaultValue = "desc")String sortDir){
        String message = messageSource.getMessage("get.success", null, LocaleContextHolder.getLocale());
        return ApiResponse.<PageResponse<WardResponse>>builder()
                .code(200)
                .message(message)
                .result(wardService.getWards(search, provinceCode, pageNumber, pageSize, sortBy, sortDir))
                .build();
    }

}
