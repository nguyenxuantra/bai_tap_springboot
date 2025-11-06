package com.orenda.country.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.orenda.country.common.mapper.BaseMapper;
import com.orenda.country.dto.request.UserRequest;
import com.orenda.country.dto.response.UserResponse;
import com.orenda.country.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel="spring", unmappedSourcePolicy = ReportingPolicy.IGNORE )
public interface UserMapper{
    User toUser(UserRequest request);
    UserResponse toUserResponse(User user);
}
