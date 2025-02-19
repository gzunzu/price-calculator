package org.gzunzu.adapter.api.mapper;

import org.gzunzu.adapter.api.dto.request.CurrencyAddRq;
import org.gzunzu.adapter.api.dto.request.CurrencyUpdateRq;
import org.gzunzu.adapter.api.dto.response.CurrencyRs;
import org.gzunzu.domain.model.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper extends BasicEntityMapper<Currency, Integer, CurrencyAddRq, CurrencyUpdateRq, CurrencyRs> {
}
