package com.card.management.configuration.exception;

import com.card.management.enumeration.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler({
            BadRequestException.class,
            UnauthorizedException.class,
    })
    ResponseEntity<APIResponse> responseResponseEntity(final Exception exception){

        ResponseEntity.BodyBuilder builder = null;

        final APIResponse response = new APIResponse(ResponseStatus.ERROR.getStatus(), ResponseStatus.ERROR.getStatusCode());

        if (exception instanceof BadRequestException badRequestException){

            builder = ResponseEntity.status(HttpStatus.BAD_REQUEST);

           return builder.body(new APIResponse(ResponseStatus.BADREQUEST.getStatus(),ResponseStatus.BADREQUEST.getStatusCode(),exception.getMessage()));

        }else if (exception instanceof UnauthorizedException unauthorizedException){
            builder = ResponseEntity.status(HttpStatus.UNAUTHORIZED);

          return builder.body(new APIResponse(ResponseStatus.UNAUTHORIZED.getStatus(),ResponseStatus.UNAUTHORIZED.getStatusCode(),exception.getMessage()));

        }else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

            new APIResponse<>(ResponseStatus.INTERNAL_ERROR.getStatus(), ResponseStatus.INTERNAL_ERROR.getStatusCode());
        }
        return builder.body(response);
    }

}
