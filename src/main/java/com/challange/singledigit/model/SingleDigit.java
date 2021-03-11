package com.challange.singledigit.model;

import com.challange.singledigit.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SingleDigit extends BaseEntity {

    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private Integer repetitions;
    private Integer result;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
