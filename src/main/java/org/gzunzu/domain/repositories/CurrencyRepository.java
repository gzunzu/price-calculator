package org.gzunzu.domain.repositories;

import org.gzunzu.domain.model.Currency;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends BasicEntityRepository<Currency, Integer> {
}
