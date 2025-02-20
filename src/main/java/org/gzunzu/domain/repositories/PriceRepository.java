package org.gzunzu.domain.repositories;

import jakarta.validation.constraints.NotNull;
import org.gzunzu.domain.model.Price;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends BasicEntityRepository<Price, Long> {

    List<Price> findByBrandId(@NotNull final Integer brandId);

    List<Price> findByProductId(@NotNull final Long productId);

    List<Price> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(@NotNull final LocalDateTime startDate, @NotNull final LocalDateTime endDate);

    List<Price> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(@NotNull final Integer brandId,
                                                                                             @NotNull final Long productId,
                                                                                             @NotNull final LocalDateTime startDate,
                                                                                             @NotNull final LocalDateTime endDate);
}
