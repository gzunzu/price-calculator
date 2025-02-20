package org.gzunzu.domain.ports;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.gzunzu.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceService extends BasicEntityService<Price, Long> {

    List<Price> getByBrandId(@NotNull final Integer brandId);

    List<Price> getByProductId(@NotNull final Long productId);

    List<Price> getByStartDateNotAfterAndEndDateNotBefore(@NotNull final LocalDateTime purchaseDate);


    Price getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(@NotNull final Integer brandId,
                                                                                         @NotNull final Long productId,
                                                                                         @NotNull final LocalDateTime purchaseDate) throws EntityNotFoundException;
}
