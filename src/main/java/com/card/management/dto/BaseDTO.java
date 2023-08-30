package com.card.management.dto;



import lombok.*;

import java.util.Date;

@Getter
@Setter
public class BaseDTO {

    private Long id;

    private Date updatedOn;

    private Long createdBy;

    private Long updatedBy;

    private Date createdOn;
}
