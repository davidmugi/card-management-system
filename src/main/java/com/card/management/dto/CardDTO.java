package com.card.management.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDTO {

    private Long userId;

    public String userFirstName;

    public String userLastName;

    private String color;

    private String description;

    private int status;

    private String statusDesc;
}
