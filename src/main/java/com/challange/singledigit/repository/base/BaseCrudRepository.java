package com.challange.singledigit.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseCrudRepository<T, K> extends JpaRepository<T, K> {
    List<T> findAllByRemovedAtIsNull();
    Optional<T> findByUidAndRemovedAtIsNull(UUID uid);
}
