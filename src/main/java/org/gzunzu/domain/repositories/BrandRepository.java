package org.gzunzu.domain.repositories;

import org.gzunzu.domain.model.Brand;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends BasicEntityRepository<Brand, Integer> {
}
