package com.orenda.country.common.globalDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncodedIdDto<TKey> {
    private String encodeId;
    private TKey key;
}
