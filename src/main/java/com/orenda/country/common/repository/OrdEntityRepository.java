package com.orenda.country.common.repository;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OrdEntityRepository<TEntity, TKey> extends JpaRepository<TEntity, TKey>,
        JpaSpecificationExecutor<TEntity> {
    default Class<TKey> getIdClass() {
        return (Class<TKey>) GenericTypeResolver.resolveTypeArguments(getClass(), OrdEntityRepository.class)[1];
    }
}
