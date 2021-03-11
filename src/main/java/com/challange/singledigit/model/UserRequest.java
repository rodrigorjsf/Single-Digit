package com.challange.singledigit.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
public class UserRequest implements Serializable {

    protected static final long serialVersionUID = 1L;

    @NotEmpty
    private String name;

    @Email
    private String email;

    public User toUser() {
        return User.builder()
                .name(this.name.strip())
                .email(this.email.strip())
                .build();
    }
}
