package com.challange.singledigit.controller;

import com.challange.singledigit.model.UserRequest;
import com.challange.singledigit.model.UserResponse;
import com.challange.singledigit.service.UserService;
import com.challange.singledigit.util.UUIDUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns a new created user."),
            @ApiResponse(code = 400, message = "Missing parameters"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(@NotNull @Valid @RequestBody UserRequest request) {
        var user = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user));
    }

    @ApiOperation(value = "Find all registered users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns all registered users."),
            @ApiResponse(code = 204, message = "No users founded."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> findAll() {
        var userList = service.findAll();
        if (CollectionUtils.isNotEmpty(userList))
            return ResponseEntity.ok(userList.stream()
                    .map(UserResponse::new)
                    .collect(Collectors.toList()));
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Find a specific user by uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a user."),
            @ApiResponse(code = 400, message = "Invalid UUID string."),
            @ApiResponse(code = 500, message = "No user founded."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{uid}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> find(@PathVariable
                                             @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid uid")
                                                     String uid) {
        var user = service.find(UUID.fromString(uid));
        return ResponseEntity.ok(new UserResponse(user));
    }

    @ApiOperation(value = "Delete a specific user by uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted."),
            @ApiResponse(code = 400, message = "Invalid UUID string."),
            @ApiResponse(code = 500, message = "No user founded."),
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{uid}")
    public ResponseEntity<UserResponse> delete(@PathVariable
                                               @Pattern(regexp = UUIDUtils.UUID_REGEXP, message = "Invalid uid")
                                                       String uid) {
        var user = service.delete(UUID.fromString(uid));
        if (Objects.nonNull(user.getRemovedAt()))
            return ResponseEntity.ok().build();
        return ResponseEntity.noContent().build();
    }
}
