package com.challange.singledigit.service;

import com.challange.singledigit.exception.ApplicationException;
import com.challange.singledigit.exception.ApplicationExceptionType;
import com.challange.singledigit.model.User;
import com.challange.singledigit.model.UserRequest;
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

import static com.challange.singledigit.exception.ApplicationExceptionType.*;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {

    private final UserRepository repository;
    private final SingleDigitRepository singleDigitRepository;

    public User create(UserRequest request) {
        var userOptional = repository.findByEmailAndRemovedAtIsNull(request.getEmail());
        if (userOptional.isPresent())
            throw new ApplicationException(EMAIL_ALREADY_USED);
        var newUser = request.toUser();
        return repository.save(newUser);
    }

    public User find(UUID uuid) {
        var userOptional = repository.findByUidAndRemovedAtIsNull(uuid);
        if (userOptional.isEmpty())
            throw new ApplicationException(ENTITY_NOT_FOUND);
        var singleDigitsList = singleDigitRepository.findAllByUser(userOptional.get());
        userOptional.get().setSingleDigitList(singleDigitsList);
        return userOptional.get();
    }

    public List<User> findAll() {
        var userList = repository.findAllByRemovedAtIsNull();
        if (CollectionUtils.isEmpty(userList))
            return Collections.emptyList();
        return userList.stream().peek(user -> {
            var singleDigitsList = singleDigitRepository.findAllByUser(user);
            user.setSingleDigitList(singleDigitsList);
        }).collect(Collectors.toList());
    }

    public User delete(UUID uuid) {
        var userOptional = repository.findByUidAndRemovedAtIsNull(uuid);
        if (userOptional.isEmpty())
            throw new ApplicationException(ENTITY_NOT_FOUND);
        userOptional.get().setRemovedAt(LocalDateTime.now());
        return repository.save(userOptional.get());
    }
}
