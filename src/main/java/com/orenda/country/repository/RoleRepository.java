package com.orenda.country.repository;

import com.orenda.country.common.repository.OrdEntityRepository;
import com.orenda.country.entity.Roles;
import com.orenda.country.enums.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends OrdEntityRepository<Roles, Integer> {
    Optional<Roles> findByName(String name);

    @Query("select p.name from Roles r join RolePermission rp on r.id = rp.rolePermissionKey.role_id join Permissions p on rp.rolePermissionKey.permission_id = p.permission_id" +
            " where r.name = :name")
    List<String> namePermission(@Param("name")String name);
}
