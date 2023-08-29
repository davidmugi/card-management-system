package com.card.management.enumeration;

import lombok.Getter;

@Getter
public enum ResponseStatus {

    FAILED("01","Failed request") , SUCESSS("00","Request proccessed successfully"),
    UNAUTHORIZED("02","Unauthorized access"),BADREQUEST("03","Bad request"),
    ERROR("03","Error processing request"),INTERNAL_ERROR("04","Internal Server error"),
    DISABLED("05","User is disabled"),INVALID_LOGIN_ATTEMPTED("06","Invalid Login Attempted");


    private String status;

    private String statusCode;

    ResponseStatus(String status, String statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }
    public static ResponseStatus toEnum(final String name) {
        for (final ResponseStatus r : values()) {
            if (r.name().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }
}
