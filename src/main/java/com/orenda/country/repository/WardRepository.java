package com.orenda.country.repository;


import com.orenda.country.entity.Wards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepository extends JpaRepository<Wards, Integer>, JpaSpecificationExecutor<Wards> {
    boolean existsByCode (String provinceCode);
    boolean existsByCodeAndIdNot(String provinceCode, int wardId);
    boolean existsByProvinceCode(String provinceCode);
}
