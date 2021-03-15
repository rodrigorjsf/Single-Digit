package com.challange.singledigit.model;

import com.challange.singledigit.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(schema = "public", name = "user")
public class User extends BaseEntity {

    @Column(columnDefinition = "text", nullable = false)
    private String name;
    @Column(columnDefinition = "text", nullable = false)
    private String email;

    private transient List<SingleDigit> singleDigitList;
}
