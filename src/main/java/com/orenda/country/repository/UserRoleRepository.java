package com.orenda.country.repository;


import com.orenda.country.common.repository.OrdEntityRepository;
import com.orenda.country.entity.UserRole;
import com.orenda.country.entity.UserRoleKey;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends OrdEntityRepository<UserRole, UserRoleKey> {
}
