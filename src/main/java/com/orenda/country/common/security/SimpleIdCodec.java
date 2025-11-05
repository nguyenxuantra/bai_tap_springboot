package com.orenda.country.common.security;

import com.orenda.country.exception.AppException;
import com.orenda.country.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class SimpleIdCodec implements IdCodec{
    private static final String SALT = "ord-secure";

    @Override
    public String encode(Object id) {
        String raw = SALT + ":" + id;
        return Base64.getUrlEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public <T> T decode(String id, Class<T> targetType) {
        try {
            String decoded = new String(Base64.getUrlDecoder().decode(encode(id)), StandardCharsets.UTF_8);
            String realId = decoded.replaceFirst(SALT + ":", "");
            if(targetType == Long.class){
                return (T) Long.valueOf(realId);
            }else if(targetType == Integer.class){
                return (T) Integer.valueOf(realId);
            }else if(targetType == String.class){
                return (T) realId;
            }
            throw new AppException(ErrorCode.ERROR_ID);
        }catch (Exception e){
            throw new AppException(ErrorCode.ERROR_ID);
        }
    }
}
