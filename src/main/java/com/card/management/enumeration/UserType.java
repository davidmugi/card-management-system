package com.card.management.enumeration;

public enum UserType {

    ADMIN(1) , MEMBER(2);

    private int code;

    UserType(int code) {
        this.code = code;
    }

    public static UserType toEnum(final int code) {
        for (final UserType u : values()) {
            if (u.code == code) {
                return u;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }
}
