package com.challange.singledigit.repository;

import com.challange.singledigit.model.User;
import com.challange.singledigit.repository.base.BaseCrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends BaseCrudRepository<User, Long> {

    Optional<User> findByEmailAndRemovedAtIsNull(String email);

    @Query(value = "UPDATE User SET removedAt = ?1 WHERE uid = ?2")
    public void deleteBySettingRemovedAt(LocalDateTime date, UUID uuid);
}
