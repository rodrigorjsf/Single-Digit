package com.challange.singledigit.model.dto;

import com.challange.singledigit.model.pojo.SingleDigit;
import com.challange.singledigit.model.pojo.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SingleDigitResponse implements Serializable {

    protected static final long serialVersionUID = 1L;

    private String number;

    private Integer repetitions;

    private Integer result;

    @JsonInclude(NON_NULL)
    private UUID userUid;

    @JsonInclude(NON_NULL)
    private String userName;

    public SingleDigitResponse(SingleDigit singleDigit, User user) {
        this.number = singleDigit.getNumber();
        this.repetitions = singleDigit.getRepetitions();
        this.result = singleDigit.getResult();
        this.userUid = user.getUid();
        this.userName = user.getName();
    }

    public SingleDigitResponse(SingleDigit singleDigit) {
        this.number = singleDigit.getNumber();
        this.repetitions = singleDigit.getRepetitions();
        this.result = singleDigit.getResult();
    }
}
