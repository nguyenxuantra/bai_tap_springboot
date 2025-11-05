package com.orenda.country.repository;

import com.orenda.country.common.repository.OrdEntityRepository;
import com.orenda.country.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends OrdEntityRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
