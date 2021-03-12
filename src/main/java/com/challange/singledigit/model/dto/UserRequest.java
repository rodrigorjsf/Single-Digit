package com.challange.singledigit.model.dto;

import com.challange.singledigit.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest implements Serializable {

    protected static final long serialVersionUID = 1L;

    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    public User toUser() {
        return User.builder()
                .name(this.name.strip())
                .email(this.email.strip())
                .build();
    }
}
