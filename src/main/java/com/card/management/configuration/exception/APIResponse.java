package com.card.management.configuration.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class APIResponse<T> {

    private String code;

    private String codeDesc;

    private T data;


    public APIResponse() {
    }

    public APIResponse(String code, String codeDesc, T data) {
        this.code = code;
        this.codeDesc = codeDesc;
        this.data = data;
    }

    public APIResponse(String code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }
}
