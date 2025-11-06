package com.orenda.country.repository;

import com.orenda.country.entity.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ProvinceRepository extends JpaRepository<Provinces, Integer>, JpaSpecificationExecutor<Provinces> {
    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String name, int provinceId);

    Set<String> findByCode(String code);

    List<Provinces> findByCodeIn(Collection<String> codes);
}
