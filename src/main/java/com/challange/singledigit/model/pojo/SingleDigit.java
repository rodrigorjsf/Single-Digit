package com.challange.singledigit.model.pojo;

import com.challange.singledigit.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(schema = "public",name = "single_digit")
public class SingleDigit extends BaseEntity {

    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private Integer repetitions;
    @Column(nullable = false)
    private Integer result;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
