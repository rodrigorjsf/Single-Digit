package com.challange.singledigit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@Setter
public class UserResponse implements Serializable {

    protected static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private UUID uid;
    @JsonInclude(NON_EMPTY)
    private List<SingleDigit> singleDigitList;

    public UserResponse(User user, List<SingleDigit> singleDigitList) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.uid = user.getUid();
        this.singleDigitList = singleDigitList;
    }

    public UserResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.uid = user.getUid();
        this.singleDigitList = Collections.emptyList();
    }

}
