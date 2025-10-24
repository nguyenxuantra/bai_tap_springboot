package com.orenda.country.repository;

import com.orenda.country.entity.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Provinces, Integer>, JpaSpecificationExecutor<Provinces> {
    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String name, int provinceId);
}
