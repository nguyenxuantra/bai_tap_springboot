package com.orenda.country.controller;


import com.orenda.country.common.apiResponse.ApiResponse;
import com.orenda.country.common.pageResponse.PageResponse;
import com.orenda.country.dto.request.WardRequest;
import com.orenda.country.dto.response.WardResponse;
import com.orenda.country.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ward")
@RequiredArgsConstructor
public class WardController {
    private final WardService wardService;

    @PostMapping
    ApiResponse<WardResponse> createWard(@RequestBody WardRequest wardRequest){
        return ApiResponse.<WardResponse>builder()
                .code(200)
                .message("Ward created successfully")
                .result(wardService.createWard(wardRequest))
                .build();
    }
    @PutMapping("/{wardId}")
    ApiResponse<WardResponse> updateWard(@RequestBody WardRequest wardRequest, @PathVariable int wardId){
        return ApiResponse.<WardResponse>builder()
                .code(200)
                .message("Ward updated successfully")
                .result(wardService.updateWard(wardRequest, wardId))
                .build();
    }
    @DeleteMapping("/{wardId}")
    ApiResponse<Void> deleteWard(@PathVariable int wardId){
        wardService.deleteWard(wardId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Ward deleted successfully")
                .build();
    }
    @GetMapping("/{wardId}")
    ApiResponse<WardResponse> getWard(@PathVariable int wardId){
        return ApiResponse.<WardResponse>builder()
                .code(200)
                .message("Ward get successfully")
                .result(wardService.getWard(wardId))
                .build();
    }
    @GetMapping
    ApiResponse<PageResponse<WardResponse>> getWards(
            @RequestParam(name = "search", required = false, defaultValue="")String search,
            @RequestParam(name = "province_code", required = false)String provinceCode,
            @RequestParam(name = "page_number", required = false, defaultValue="1")int pageNumber,
            @RequestParam(name = "page_size", required = false, defaultValue = "5")int pageSize,
            @RequestParam(name = "sort_by", required =false, defaultValue = "id")String sortBy,
            @RequestParam(name = "sort_dir", required = false, defaultValue = "desc")String sortDir){
        return ApiResponse.<PageResponse<WardResponse>>builder()
                .code(200)
                .message("get wards success")
                .result(wardService.getWards(search, provinceCode, pageNumber, pageSize, sortBy, sortDir))
                .build();
    }

}
