package org.gzunzu.domain.ports;

import jakarta.persistence.EntityNotFoundException;
import org.gzunzu.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceService extends BasicEntityService<Price, Long> {

    List<Price> getByBrandId(final Integer brandId);

    List<Price> getByProductId(final Long productId);

    List<Price> getByStartDateNotAfterAndEndDateNotBefore(final LocalDateTime startDate, final LocalDateTime endDate);


    Price getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(final Integer brandId,
                                                                                         final Long productId,
                                                                                         final LocalDateTime purchaseDate) throws EntityNotFoundException;
}
