package com.challange.singledigit.model;

import com.challange.singledigit.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "user")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;

    private transient List<SingleDigit> singleDigitList;
}
