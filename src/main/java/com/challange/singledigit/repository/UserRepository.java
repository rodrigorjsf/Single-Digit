package com.challange.singledigit.repository;

import com.challange.singledigit.model.pojo.User;
import com.challange.singledigit.repository.base.BaseCrudRepository;

import java.util.Optional;


public interface UserRepository extends BaseCrudRepository<User, Long> {

    Optional<User> findByEmailAndRemovedAtIsNull(String email);
}
