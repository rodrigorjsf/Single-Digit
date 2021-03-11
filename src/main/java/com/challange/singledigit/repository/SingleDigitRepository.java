package com.challange.singledigit.repository;

import com.challange.singledigit.model.pojo.SingleDigit;
import com.challange.singledigit.model.pojo.User;
import com.challange.singledigit.repository.base.BaseCrudRepository;

import java.util.List;

public interface SingleDigitRepository extends BaseCrudRepository<SingleDigit, Long> {
    List<SingleDigit> findAllByUser(User user);
}
