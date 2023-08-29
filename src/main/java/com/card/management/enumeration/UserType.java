package com.card.management.enumeration;

public enum UserType {

    ADMIN(1) , MEMBER(2);

    private int code;

    UserType(int code) {
        this.code = code;
    }

    public static UserType toEnum(final String name) {
        for (final UserType u : values()) {
            if (u.name().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }
}
