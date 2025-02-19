package org.gzunzu.adapter.api.mapper;

import org.gzunzu.adapter.api.dto.request.RateAddRq;
import org.gzunzu.adapter.api.dto.request.RateUpdateRq;
import org.gzunzu.adapter.api.dto.response.RateRs;
import org.gzunzu.domain.model.Rate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RateMapper extends BasicEntityMapper<Rate, Long, RateAddRq, RateUpdateRq, RateRs> {
}
