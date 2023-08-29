package com.card.management.entity;

import com.card.management.configuration.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "names")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private User user;

    @Column(name = "color")
    private String color;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;
}
