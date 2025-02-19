package org.gzunzu.domain.repositories;

import org.gzunzu.domain.model.BasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BasicEntityRepository<E extends BasicEntity<E, K>, K> extends JpaRepository<E, K> {
}
