package com.card.management.configuration;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "updated_on")
    private Date updatedOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_on")
    protected Date createdOn;

    public BaseEntity createdOn(Long userId) {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Africa/Nairobi"));
        final Date date = Date.from(dateTime.atZone(ZoneId.of("Africa/Nairobi")).toInstant());
        this.updatedOn = date;
        this.createdOn = date;
        this.updatedBy = userId;
        this.createdBy = userId;
        return this;
    }

    public BaseEntity updatedOn(Long userId) {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Africa/Nairobi"));
        final Date date = Date.from(dateTime.atZone(ZoneId.of("Africa/Nairobi")).toInstant());
        this.updatedOn = date;
        this.updatedBy = userId;
        return this;
    }
}

