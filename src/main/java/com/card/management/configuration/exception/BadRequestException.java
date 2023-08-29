package com.card.management.configuration.exception;

import com.card.management.enumeration.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadRequestException extends Exception{

    private final ResponseStatus responseStatus = ResponseStatus.BADREQUEST;

    public BadRequestException(String message) {
        super(message);
    }
}
