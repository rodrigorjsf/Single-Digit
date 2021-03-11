package com.challange.singledigit.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SingleDigitRequest implements Serializable {

    protected static final long serialVersionUID = 1L;

    @NotEmpty
    private String number;

    @NotEmpty
    private Integer repetitions;

    private UUID userUid;

    public SingleDigit toSingleDigit() {
        return SingleDigit.builder()
                .number(this.number)
                .repetitions(this.repetitions)
                .build();
    }
}
