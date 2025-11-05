package com.orenda.country.common.service;

import com.orenda.country.common.entity.BaseEntity;
import com.orenda.country.common.globalDto.CommonResultDto;
import com.orenda.country.common.globalDto.EncodedIdDto;
import com.orenda.country.common.globalDto.PageResultDto;
import com.orenda.country.common.globalDto.PagedResultRequestDto;
import com.orenda.country.common.mapper.BaseMapper;
import com.orenda.country.common.repository.OrdEntityRepository;
import com.orenda.country.common.security.IdCodec;
import com.orenda.country.exception.AppException;
import com.orenda.country.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Slf4j
@Service
@Transactional(readOnly = true)
public abstract class CrudAppService<
        TEntity extends BaseEntity<TKey>,
        TKey,
        TEntityDto,
        TGetListInput extends PagedResultRequestDto,
        TPagedOutputDto extends EncodedIdDto<TKey>,
        TCreateInput,
        TUpdateInput
        > {

    @Autowired
    protected IdCodec idCodec;

    protected TEntityDto getEntityDtoByEncodedId(String encodedId) {
        var entity = finEntityByEncodedId(encodedId);
        if (entity == null) {
            return null;
        }
        return convertEntityToDto(entity);
    }


    protected TEntity  finEntityByEncodedId(String encodedId) {
        TKey readId = decodeId(encodedId);
        return getRepository().findById(readId).orElse(null);
    }

    @GetMapping("/get-by-id/{id}")
    public CommonResultDto<TEntityDto> getByEncodeId(@PathVariable("id") String encodeId){
        var dto = getEntityDtoByEncodedId(encodeId);
        if(dto == null){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return CommonResultDto.success(dto);
    }

//    @PostMapping(value = "/get-paged", produces = "application/json")
//    public PageResultDto<TPagedOutputDto> getpagedList(
//            @RequestBody TGetListInput input
//    ){
//
//    }

//    protected PageResultDto<TPagedOutputDto> buildPageResult(TGetListInput input){
//        var pageRequest = input.getPageRequest();
//        var spec = buildSpecificationForPaging(input);
//        if(spec != null){
//            var pageResult = getRepository().findAll(spec, pageRequest);
//            return
//        }
//    }

    protected Specification<TEntity> buildSpecificationForPaging(TGetListInput input) {
        return null;
    }
    // =========== Mapping Methods ==========
    protected TEntityDto convertEntityToDto(TEntity entity) {
        return baseMapper().toDto(entity);
    }
//    protected PageResultDto<TPagedOutputDto> convertPageToPagedDto(Page<TEntity> page) {
//        if (page == null) {
//            return new PageResultDto<>(0, new ArrayList<>());
//        }
//
//        // Nếu không có dữ liệu
//        if (page.getContent().isEmpty()) {
//            return new PageResultDto<>(page.getTotalElements(), new ArrayList<>());
//        }
//
//        // Chuyển đổi từng entity sang DTO
//        var items = page.getContent().stream()
//                .map(this::convertEntityToPagedDto)
//                .toList();
//
//        return new PageResultDto<>(page.getTotalElements(), items);
//    }
//    protected TPagedOutputDto convertEntityToPagedDto(TEntity entity) {
//        try {
////            return objectMapper.map(entity, getPagedOutputDtoClass());
//        } catch (Exception e) {
//            log.error("Error mapping entity to paged output DTO: {}", e.getMessage());
//            return null;
//        }
//    }
    // =========== Validation Methods =================
    // Các method này có thể được override để implement validation logic
    protected void validationBeforeCreate(TCreateInput input){
    }
    protected void validationBeforeUpdate(TUpdateInput updateInput, TEntity entityToUpdate){
    }
    protected void validationBeforeRemove(TEntity entityToRemove){
    }
    protected void hasRole(String role){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        boolean hasRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r->r.equalsIgnoreCase(role));
        if(!hasRole){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }
    // =========== lifecycle Hook Methods =============
    // Các method được gọi sau khi hoàn thành thao tác CRUD
    protected void handleAfterCreate(TEntity createEntity, TCreateInput createInput) {
    }

    protected void handleAfterUpdate(TEntity updateEntity, TUpdateInput updateInput) {

    }
    protected void handleAfterRemove(TEntity removeEntity) {
    }

    // =========== ID Encoding/Decoding Methods =======

    protected TKey decodeId(String encodedId) {return idCodec.decode(encodedId,getKeyClassFromRepository());}

    protected String encodeId(TKey id) {return idCodec.encode(id);}

    private Class<TKey> getKeyClassFromRepository(){ return (Class<TKey>) getRepository().getIdClass();}


    // ============ Abstract Methods ================
    // Các method bắt buộc phải implement trong class con

    protected abstract OrdEntityRepository<TEntity, TKey> getRepository();

    protected abstract Class<TEntityDto> getEntityDtoClass();

    protected abstract BaseMapper<TEntityDto, TEntity> baseMapper();

    protected abstract Class<TPagedOutputDto> getPagedOutputDtoClass();

    protected abstract String getEntityName();

    protected abstract Class<TEntity> getEntityClass();

}
