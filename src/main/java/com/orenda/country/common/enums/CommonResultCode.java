package com.orenda.country.common.enums;


public enum CommonResultCode {
    SUCCESS("200"),
    BAD_REQUEST("400"),
    FORBIDDEN("403"),
    NOT_FOUND("404"),
    ERR_SERVER("500")
    ;
    private final String text;
    CommonResultCode(String text) {
        this.text = text;
    }
    @Override
    public String toString() {return text;}
}
