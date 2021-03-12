package com.challange.singledigit.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
public class KeyRequest implements Serializable {

    @NotEmpty
    private String base64EncodedKey;

}
