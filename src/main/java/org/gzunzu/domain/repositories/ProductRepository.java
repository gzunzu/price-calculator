package org.gzunzu.domain.repositories;

import org.gzunzu.domain.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BasicEntityRepository<Product, Long> {
}
