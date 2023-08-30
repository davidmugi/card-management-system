package com.card.management.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO extends BaseDTO{

    private Long userId;

    private String userFirstName;

    private String userLastName;

    private String color;

    @NotNull(message = "Card name is required")
    private String name;

    private String description;

    private int status;

    private String statusDesc;
}
