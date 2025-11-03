package com.orenda.country.common.globalDto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.orenda.country.common.enums.CommonResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResultDto <TResult>  implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    private TResult result;
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String,Object> additionalProperties;

    @JsonIgnore
    public CommonResultDto<TResult> additionalProperties (String key , Object value) {
        if(this.additionalProperties == null) {
            this.additionalProperties = new TreeMap<>();
        }
        this.additionalProperties.put(key,value);
        return this;
    }
    public Boolean getIsSuccessful(){
        return CommonResultCode.SUCCESS.toString().equals(this.code);
    }

    public static <T> CommonResultDto<T> success(){
        return CommonResultDto.<T>builder()
                .code(CommonResultCode.SUCCESS.toString())
                .message("success")
                .build();
    }
    public static <T> CommonResultDto<T> success(T data){
        return CommonResultDto.<T>builder()
                .code(CommonResultCode.SUCCESS.toString())
                .message("success")
                .result(data)
                .build();
    }
    public static <T> CommonResultDto<T> success(T data, String message){
        return CommonResultDto.<T>builder()
                .code(CommonResultCode.SUCCESS.toString())
                .message(message)
                .result(data)
                .build();
    }
    public static <T> CommonResultDto<T> fail(String message){
        return CommonResultDto.<T>builder()
                .code(CommonResultCode.ERR_SERVER.toString())
                .message(message)
                .build();
    }
    public static <T> CommonResultDto<T> fail(String code, String message){
        return CommonResultDto.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
    public static <T> CommonResultDto<T> fail(CommonResultCode resultCode, String message){
        return fail(resultCode.toString(),message);
    }
}
