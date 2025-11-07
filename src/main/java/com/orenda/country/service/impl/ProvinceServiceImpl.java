package com.orenda.country.service.impl;

import com.orenda.country.common.pageResponse.PageResponse;
import com.orenda.country.common.repository.spec.SpecificationBuilder;
import com.orenda.country.configuration.RedisConfig;
import com.orenda.country.dto.request.ProvinceRequest;
import com.orenda.country.dto.response.ProvinceResponse;
import com.orenda.country.entity.Provinces;
import com.orenda.country.exception.AppException;
import com.orenda.country.exception.ErrorCode;
import com.orenda.country.mapper.ProvinceMapper;
import com.orenda.country.repository.ProvinceRepository;
import com.orenda.country.repository.WardRepository;
import com.orenda.country.service.ProvinceService;
import com.orenda.country.specification.ProvinceSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;
    private final WardRepository wardRepository;
    private final RedisConfig redisConfig;

    @Override
    @CacheEvict(value = "provinceDropdown", allEntries = true)
    public ProvinceResponse createProvince(ProvinceRequest request){
        if(provinceRepository.existsByCode(request.getCode())){
            throw new AppException(ErrorCode.CODE_EXISTED);
        }
        Provinces province = provinceMapper.toProvince(request);
        Provinces provinceSave = provinceRepository.save(province);
        return provinceMapper.toResponse(provinceSave);
    }
    @Override
    @Transactional
    @CacheEvict(value = "provinceDropdown", allEntries = true)
    public ProvinceResponse updateProvince(ProvinceRequest request, int provinceId){
        Provinces province = provinceRepository.findById(provinceId).orElseThrow(()-> new AppException(ErrorCode.PROVINCE_NOT_FOUND));
        if(provinceRepository.existsByCodeAndIdNot(request.getCode(), provinceId)) {
            throw new AppException(ErrorCode.CODE_EXISTED);
        }

        provinceMapper.updateProvince(request, province);
        return provinceMapper.toResponse(province);
    }
    @Override
    public ProvinceResponse getDetailProvince(int provinceId){
        Provinces provinces = provinceRepository.findById(provinceId).orElseThrow(()-> new AppException(ErrorCode.PROVINCE_NOT_FOUND));
        return provinceMapper.toResponse(provinces);
    }
    @Override
    @CacheEvict(value = "provinceDropdown", allEntries = true)
    public void deleteProvince(int provinceId){
        Provinces provinces = provinceRepository.findById(provinceId).orElseThrow(()-> new AppException(ErrorCode.PROVINCE_NOT_FOUND));
        if(wardRepository.existsByProvinceCode(provinces.getCode())){
            throw new AppException(ErrorCode.CODE_EXISTED_IN_WARD);
        }
        provinceRepository.deleteById(provinceId);
    }
    @Override
    @Cacheable(value = "provinceDropdown", key = "#search + '_' + #pageNumber + '_' + #pageSize + '_' + #sortBy + '_' + #sortDir")
    public PageResponse<ProvinceResponse> getAllProvince(String search, int pageNumber, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        SpecificationBuilder<Provinces> builder = SpecificationBuilder.builder();
        builder.searchAll("code", search)
                .searchAll("name", search);
        Specification<Provinces> spect = builder.buildOr();
        Page<Provinces> provincesPage = provinceRepository.findAll(spect,pageable);
        List<ProvinceResponse> provincesList = provinceMapper.toListProvinceResponse(provincesPage.getContent());
        return PageResponse.<ProvinceResponse>builder()
                .content(provincesList)
                .pageNumber(provincesPage.getNumber())
                .pageSize(provincesPage.getSize())
                .totalElement(provincesPage.getTotalElements())
                .totalPages(provincesPage.getTotalPages())
                .isLast(provincesPage.isLast())
                .build();
    }
}
