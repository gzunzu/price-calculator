package org.gzunzu.domain.repositories;

import org.gzunzu.domain.model.Rate;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends BasicEntityRepository<Rate, Long> {
}
