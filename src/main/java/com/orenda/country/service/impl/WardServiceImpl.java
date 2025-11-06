package com.orenda.country.service.impl;


import com.orenda.country.common.pageResponse.PageResponse;
import com.orenda.country.dto.request.WardRequest;
import com.orenda.country.dto.response.WardResponse;
import com.orenda.country.entity.Provinces;
import com.orenda.country.entity.Wards;
import com.orenda.country.exception.AppException;
import com.orenda.country.exception.ErrorCode;
import com.orenda.country.mapper.WardMapper;
import com.orenda.country.repository.ProvinceRepository;
import com.orenda.country.repository.WardRepository;
import com.orenda.country.service.WardService;
import com.orenda.country.specification.WardSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WardServiceImpl implements WardService {
    private final WardRepository wardRepository;
    private final ProvinceRepository provinceRepository;
    private final WardMapper wardMapper;

    @Override
    public WardResponse createWard(WardRequest request) {
        if (wardRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.CODE_EXISTED);
        }
        if (!provinceRepository.existsByCode(request.getProvinceCode())) {
            throw new AppException(ErrorCode.PROVINCE_NOT_FOUND);
        }
        Wards ward = wardMapper.toWard(request);
        Wards wardResponse = wardRepository.save(ward);
        return wardMapper.toResponse(wardResponse);
    }

    @Override
    @Transactional
    public WardResponse updateWard(WardRequest request, int wardId){
        if (wardRepository.existsByCodeAndIdNot(request.getCode(), wardId)) {
            throw new AppException(ErrorCode.CODE_EXISTED);
        }
        if (!provinceRepository.existsByCode(request.getProvinceCode())) {
            throw new AppException(ErrorCode.PROVINCE_NOT_FOUND);
        }
        Wards ward = wardRepository.findById(wardId).orElseThrow(()-> new AppException(ErrorCode.WARD_NOT_FOUND));
        wardMapper.updateWard(request, ward);
        return wardMapper.toResponse(ward);
    }

    @Override
    public void deleteWard(int wardId) {
        wardRepository.findById(wardId).orElseThrow(()-> new AppException(ErrorCode.WARD_NOT_FOUND));
        wardRepository.deleteById(wardId);
    }
    @Override
    public WardResponse getWard(int wardId) {
        Wards wards = wardRepository.findById(wardId).orElseThrow(()-> new AppException(ErrorCode.WARD_NOT_FOUND));
        return wardMapper.toResponse(wards);
    }
    @Override
    public PageResponse<WardResponse> getWards(String search, String provinceCode, int pageNumber, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize, sort);
        Specification<Wards> spec = Specification.allOf(WardSpecification.searchAll(search), WardSpecification.filterProvinceCode(provinceCode));
        Page<Wards> pageWards = wardRepository.findAll(spec, pageable);
        // lấy toàn bộ provinceCode có trong wards hiện tại
        Set<String> provinceCodes= pageWards.stream().map(Wards::getProvinceCode
        ).collect(Collectors.toSet());
        // lấy danh sách cách province
        List<Provinces> provinces = provinceRepository.findByCodeIn(provinceCodes);
        // Tạo map
        Map<String, String> provinceMap = provinces.stream().collect(Collectors.toMap(Provinces::getCode, Provinces::getName));
        List<WardResponse> listWards = pageWards.stream().map(wards ->{
            return WardResponse.builder()
                    .id(wards.getId())
                    .name(wards.getName())
                    .code(wards.getCode())
                    .provinceCode(wards.getProvinceCode())
                    .provinceName(provinceMap.get(wards.getProvinceCode()))
                    .build();
        }).collect(Collectors.toList());
        return PageResponse.<WardResponse>builder()
                .content(listWards)
                .pageSize(pageWards.getSize())
                .pageNumber(pageWards.getNumber())
                .totalElement(pageWards.getTotalElements())
                .totalPages(pageWards.getTotalPages())
                .isLast(pageWards.isLast())
                .build();
    }
}
