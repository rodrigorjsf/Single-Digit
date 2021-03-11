package com.challange.singledigit.model.dto;

import com.challange.singledigit.model.pojo.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@Setter
public class UserResponse implements Serializable {

    protected static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private UUID uid;
    @JsonInclude(NON_EMPTY)
    private List<SingleDigitResponse> singleDigitList;

    public UserResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.uid = user.getUid();
        this.singleDigitList = CollectionUtils.isNotEmpty(user.getSingleDigitList()) ?
                user.getSingleDigitList()
                        .stream()
                        .map(SingleDigitResponse::new)
                        .collect(Collectors.toList()) : Collections.emptyList();
    }

}
