package com.orenda.country.mapper;

import com.orenda.country.dto.request.ProvinceRequest;
import com.orenda.country.dto.response.ProvinceResponse;
import com.orenda.country.entity.Provinces;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy =  ReportingPolicy.IGNORE)
public interface ProvinceMapper {
    Provinces toProvince(ProvinceRequest request);
    ProvinceResponse toResponse(Provinces provinces);
    void updateProvince(ProvinceRequest request, @MappingTarget Provinces provinces);
    List<ProvinceResponse> toListProvinceResponse(List<Provinces> provinces);
}
