package com.card.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends BaseDTO{

    private String firstName;

    private String lastname;

    private String email;

    private String password;

    private int userType;

    private String userTypeDesc;

    private boolean isEnabled;
}
