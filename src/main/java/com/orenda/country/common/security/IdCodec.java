package com.orenda.country.common.security;

public interface IdCodec {
    String encode(Object id);
    <T> T decode(String id, Class<T> targetType);
}
