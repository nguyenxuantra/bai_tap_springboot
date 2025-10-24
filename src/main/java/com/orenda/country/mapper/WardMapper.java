package com.orenda.country.mapper;


import com.orenda.country.dto.request.WardRequest;
import com.orenda.country.dto.response.WardResponse;
import com.orenda.country.entity.Wards;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy =  ReportingPolicy.IGNORE)
public interface WardMapper {
    WardResponse toResponse(Wards wards);
    Wards toWard(WardRequest request);
    void updateWard(WardRequest request, @MappingTarget Wards wards);
    List<WardResponse> toListWard(List<Wards> wards);
}
