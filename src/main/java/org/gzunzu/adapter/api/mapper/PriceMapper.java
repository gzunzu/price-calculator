package org.gzunzu.adapter.api.mapper;

import org.gzunzu.adapter.api.dto.request.PriceAddRq;
import org.gzunzu.adapter.api.dto.request.PriceUpdateRq;
import org.gzunzu.adapter.api.dto.response.PricePriorityRs;
import org.gzunzu.adapter.api.dto.response.PriceRs;
import org.gzunzu.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PriceMapper extends BasicEntityMapper<Price, Long, PriceAddRq, PriceUpdateRq, PriceRs> {

    @Override
    @Mapping(source = "brandId", target = "brand.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "rateId", target = "rate.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "currencyId", target = "currency.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "productId", target = "product.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Price toEntity(final PriceAddRq rq);

    @Override
    @Mapping(source = "brandId", target = "brand.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "rateId", target = "rate.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "currencyId", target = "currency.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "productId", target = "product.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Price toEntity(final PriceUpdateRq rq);

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "rate.id", target = "rateId")
    @Mapping(source = "currency.name", target = "currencyName")
    @Mapping(source = "product.id", target = "productId")
    PricePriorityRs toPricePriorityRs(final Price entity);
}
