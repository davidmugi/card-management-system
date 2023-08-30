package com.card.management.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDTO extends BaseDTO{

    private Long userId;

    private String userFirstName;

    private String userLastName;

    private String color;

    private String description;

    private int status;

    private String statusDesc;
}
