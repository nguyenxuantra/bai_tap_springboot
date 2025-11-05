package com.orenda.country.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    PROVINCE_NOT_FOUND(1007, "Tỉnh không tồn tại", HttpStatus.NOT_FOUND),
    CODE_EXISTED(1008, "Mã code đã tồn tại", HttpStatus.CONFLICT),
    CODE_EXISTED_IN_WARD(1009,"Mã tỉnh đang được dùng cho xã", HttpStatus.CONFLICT ),
    WARD_NOT_FOUND(1010, "Xã không tồn tại", HttpStatus.NOT_FOUND),
    ERROR_ID(1011,"Lỗi id", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1012, "Không tìm thấy",  HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1013, "Không tìm thấy người dùng đăng nhập", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS(1014, "Tài khoản và mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
