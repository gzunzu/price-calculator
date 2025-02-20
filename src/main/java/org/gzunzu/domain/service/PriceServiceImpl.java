package org.gzunzu.domain.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.gzunzu.domain.model.Price;
import org.gzunzu.domain.ports.PriceService;
import org.gzunzu.domain.repositories.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceServiceImpl extends BasicEntityServiceImpl<Price, Long> implements PriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(final PriceRepository priceRepository) {
        super(priceRepository);
        this.priceRepository = priceRepository;
    }

    @Override
    public final List<Price> getByBrandId(@NotNull final Integer brandId) {
        return priceRepository.findByBrandId(brandId);
    }

    @Override
    public final List<Price> getByProductId(@NotNull final Long productId) {
        return priceRepository.findByProductId(productId);
    }

    @Override
    public List<Price> getByStartDateNotAfterAndEndDateNotBefore(@NotNull final LocalDateTime purchaseDate) {
        return priceRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(purchaseDate, purchaseDate);
    }

    @Override
    public final Price getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(@NotNull final Integer brandId,
                                                                                                      @NotNull final Long productId,
                                                                                                      @NotNull final LocalDateTime purchaseDate) throws EntityNotFoundException {
        return priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(brandId,
                        productId,
                        purchaseDate,
                        purchaseDate).stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(() -> new EntityNotFoundException(String.format("No Price configured in DB with " +
                                "brandId «%d», productId «%d» and date «%s».",
                        brandId,
                        productId,
                        purchaseDate)));
    }

    @Override
    protected String getEntityClassName() {
        return Price.class.getSimpleName();
    }
}
