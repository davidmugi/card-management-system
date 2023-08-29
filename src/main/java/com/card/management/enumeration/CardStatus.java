package com.card.management.enumeration;

public enum CardStatus {

    TODO(1) , INPROGRESS(2) , DONE(3);

    private int statusCode;

    CardStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public static CardStatus toEnum(final int code) {
        for (final CardStatus c : values()) {
            if (c.statusCode ==  code) {
                return c;
            }
        }
        return null;
    }
}
