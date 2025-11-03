package com.orenda.country.common.service;


import com.fasterxml.jackson.databind.util.TypeKey;
import com.orenda.country.common.entity.BaseEntity;
import com.orenda.country.common.globalDto.EncodedIdDto;
import com.orenda.country.common.globalDto.PagedResultRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public abstract class CrudAppService<
        TEntity extends BaseEntity<TKey>,
        TKey,
        TEntityDto,
        TGetListInput extends PagedResultRequestDto,
        TPagedOutput extends EncodedIdDto<TKey>,
        ICreateInput,
        TUpdateInput
        > {

}
