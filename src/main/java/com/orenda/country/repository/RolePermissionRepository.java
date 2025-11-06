package com.orenda.country.repository;


import com.orenda.country.common.repository.OrdEntityRepository;
import com.orenda.country.entity.RolePermission;
import com.orenda.country.entity.RolePermissionKey;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface RolePermissionRepository extends OrdEntityRepository<RolePermission, RolePermissionKey> {
}
