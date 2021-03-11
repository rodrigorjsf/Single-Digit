package com.challange.singledigit.service;

import com.challange.singledigit.exception.ApplicationException;
import com.challange.singledigit.exception.ApplicationExceptionType;
import com.challange.singledigit.model.UserRequest;
import com.challange.singledigit.model.UserResponse;
import com.challange.singledigit.repository.SingleDigitRepository;
import com.challange.singledigit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {

    private final UserRepository repository;
    private final SingleDigitRepository singleDigitRepository;

    public UserResponse create(UserRequest request) {
        var userOptional = repository.findByEmailAndRemovedAtIsNull(request.getEmail());
        if (userOptional.isPresent())
            throw new ApplicationException(ApplicationExceptionType.EMAIL_ALREADY_USED);
        var newUser = request.toUser();
        newUser = repository.save(newUser);
        return new UserResponse(newUser);
    }

    public UserResponse find(UUID uuid) {
        var userOptional = repository.findByUidAndRemovedAtIsNull(uuid);
        if (userOptional.isEmpty())
            throw new ApplicationException(ApplicationExceptionType.ENTITY_NOT_FOUND);
        var singleDigitsList = singleDigitRepository.findAllByUser(userOptional.get());
        return new UserResponse(userOptional.get(), singleDigitsList);
    }

    public List<UserResponse> findAll() {
        var userList = repository.findAllByRemovedAtIsNull();
        if (CollectionUtils.isEmpty(userList))
            return Collections.emptyList();
        return userList.stream().map(user -> {
            var singleDigitsList = singleDigitRepository.findAllByUser(user);
            return new UserResponse(user, singleDigitsList);
        }).collect(Collectors.toList());
    }

    public void delete(UUID uuid) {
        var userOptional = repository.findByUidAndRemovedAtIsNull(uuid);
        if (userOptional.isEmpty())
            throw new ApplicationException(ApplicationExceptionType.ENTITY_NOT_FOUND);
        repository.deleteBySettingRemovedAt(LocalDateTime.now(), userOptional.get().getUid());
    }
}
