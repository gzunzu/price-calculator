package org.gzunzu.domain.service;

import jakarta.persistence.EntityNotFoundException;
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
    public final List<Price> getByBrandId(final Integer brandId) {
        return priceRepository.findByBrandId(brandId);
    }

    @Override
    public final List<Price> getByProductId(final Long productId) {
        return priceRepository.findByProductId(productId);
    }

    @Override
    public List<Price> getByStartDateNotAfterAndEndDateNotBefore(final LocalDateTime startDate, final LocalDateTime endDate) {
        return priceRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(startDate, endDate);
    }

    @Override
    public final Price getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(final Integer brandId,
                                                                                                      final Long productId,
                                                                                                      final LocalDateTime purchaseDate) throws EntityNotFoundException {
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
