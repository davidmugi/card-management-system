package com.card.management.configuration.exception;

import com.card.management.enumeration.ResponseStatus;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UnauthorizedException extends Exception{

    private ResponseStatus responseStatus = ResponseStatus.UNAUTHORIZED;

    public UnauthorizedException(String message) {
        super(message);
    }
}
