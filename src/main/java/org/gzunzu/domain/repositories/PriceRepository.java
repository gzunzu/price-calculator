package org.gzunzu.domain.repositories;

import org.gzunzu.domain.model.Price;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends BasicEntityRepository<Price, Long> {

    List<Price> findByBrandId(final Integer brandId);

    List<Price> findByProductId(final Long productId);

    List<Price> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(final LocalDateTime startDate, final LocalDateTime endDate);

    List<Price> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(final Integer brandId,
                                                                                             final Long productId,
                                                                                             final LocalDateTime startDate,
                                                                                             final LocalDateTime endDate);
}
