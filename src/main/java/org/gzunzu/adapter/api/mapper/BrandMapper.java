package org.gzunzu.adapter.api.mapper;

import org.gzunzu.adapter.api.dto.request.BrandAddRq;
import org.gzunzu.adapter.api.dto.request.BrandUpdateRq;
import org.gzunzu.adapter.api.dto.response.BrandRs;
import org.gzunzu.domain.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper extends BasicEntityMapper<Brand, Integer, BrandAddRq, BrandUpdateRq, BrandRs> {
}
